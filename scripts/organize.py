#!/usr/bin/env python3
"""
What it does:
1. Scans the repo for problem folders LeetHub has created (it names folders
   like "1-two-sum", "1two-sum", or similar depending on version).
2. Fetches each problem's topic tags from LeetCode's public GraphQL API.
3. Moves the folder into topics/<primary-topic>/<problem-slug>/ if it
   isn't already organized.
4. Writes/refreshes a README.md inside each problem folder with the
   problem title, difficulty, topics, and links to the solution file.
5. Rebuilds the root README.md with a solved-count table, difficulty
   breakdown, and a topic-wise index.
"""

import json
import os
import re
import shutil
import time
import urllib.request
from collections import defaultdict
from pathlib import Path

REPO_ROOT = Path(os.environ.get("REPO_ROOT", ".")).resolve()
TOPICS_DIR = REPO_ROOT / "topics"
STATE_FILE = REPO_ROOT / ".leetcode_meta_cache.json"

LEETCODE_GRAPHQL = "https://leetcode.com/graphql"

# Folder/file names LeetHub-style extensions typically produce that we
# should ignore when scanning for problem folders.
IGNORE_DIRS = {
    ".git", ".github", "topics", "scripts", "node_modules", ".vercel"
}

SLUG_RE = re.compile(r"^\d+[-.]?(.+)$")


def slugify_from_dirname(name: str) -> str:
    """LeetHub folders are usually like '1-two-sum' -> slug 'two-sum'."""
    m = SLUG_RE.match(name)
    base = m.group(1) if m else name
    return base.strip("-").lower()


def load_cache():
    if STATE_FILE.exists():
        return json.loads(STATE_FILE.read_text())
    return {}


def save_cache(cache):
    STATE_FILE.write_text(json.dumps(cache, indent=2, ensure_ascii=False))


def fetch_problem_meta(slug: str):
    """Query LeetCode's public GraphQL endpoint for tags/difficulty/title."""
    query = {
        "operationName": "questionData",
        "variables": {"titleSlug": slug},
        "query": """
        query questionData($titleSlug: String!) {
          question(titleSlug: $titleSlug) {
            questionFrontendId
            title
            difficulty
            topicTags { name slug }
          }
        }
        """,
    }
    req = urllib.request.Request(
        LEETCODE_GRAPHQL,
        data=json.dumps(query).encode(),
        headers={"Content-Type": "application/json", "User-Agent": "leetcode-organizer"},
    )
    try:
        with urllib.request.urlopen(req, timeout=10) as resp:
            data = json.loads(resp.read())
            return data["data"]["question"]
    except Exception as e:
        print(f"  ! could not fetch metadata for '{slug}': {e}")
        return None


def find_unorganized_problem_dirs():
    """Top-level dirs at repo root that look like LeetHub problem folders."""
    found = []
    for entry in REPO_ROOT.iterdir():
        if not entry.is_dir() or entry.name in IGNORE_DIRS:
            continue
        if entry.name.startswith("."):
            continue
        found.append(entry)
    return found


def find_solution_file(problem_dir: Path):
    for f in problem_dir.iterdir():
        if f.is_file() and f.suffix in {".py", ".java", ".cpp", ".js", ".ts", ".c"}:
            return f
    return None


def write_problem_readme(dest_dir: Path, meta: dict, solution_filename: str):
    tags = ", ".join(t["name"] for t in meta.get("topicTags", []))
    content = f"""# {meta['questionFrontendId']}. {meta['title']}

**Difficulty:** {meta['difficulty']} | **Topics:** {tags or 'N/A'}

## Problem
[View on LeetCode](https://leetcode.com/problems/{dest_dir.name}/)

## Approach
_Add a short note on your approach and why you chose it._

## Complexity
- Time: _fill in_
- Space: _fill in_

## Solution
See [`{solution_filename}`](./{solution_filename})
"""
    (dest_dir / "README.md").write_text(content)


def organize():
    cache = load_cache()
    TOPICS_DIR.mkdir(exist_ok=True)

    moved_count = 0
    for problem_dir in find_unorganized_problem_dirs():
        slug = slugify_from_dirname(problem_dir.name)
        if slug in cache and cache[slug].get("organized"):
            continue

        print(f"Processing: {problem_dir.name} -> slug '{slug}'")
        meta = fetch_problem_meta(slug)
        time.sleep(0.5)  # be polite to the public endpoint

        if not meta:
            continue

        primary_topic = meta["topicTags"][0]["slug"] if meta.get("topicTags") else "misc"
        dest_dir = TOPICS_DIR / primary_topic / slug
        dest_dir.mkdir(parents=True, exist_ok=True)

        for item in problem_dir.iterdir():
            shutil.move(str(item), str(dest_dir / item.name))
        problem_dir.rmdir()

        solution_file = find_solution_file(dest_dir)
        write_problem_readme(dest_dir, meta, solution_file.name if solution_file else "solution")

        cache[slug] = {
            "organized": True,
            "topic": primary_topic,
            "difficulty": meta["difficulty"],
            "title": meta["title"],
            "id": meta["questionFrontendId"],
        }
        moved_count += 1

    save_cache(cache)
    print(f"Organized {moved_count} new problem(s).")
    return cache


def build_root_readme(cache: dict):
    by_difficulty = defaultdict(int)
    by_topic = defaultdict(list)

    for slug, info in cache.items():
        by_difficulty[info["difficulty"]] += 1
        by_topic[info["topic"]].append((info["id"], info["title"], slug))

    total = len(cache)
    easy = by_difficulty.get("Easy", 0)
    medium = by_difficulty.get("Medium", 0)
    hard = by_difficulty.get("Hard", 0)

    lines = []
    lines.append("# LeetCode Solutions\n")
    lines.append("Automatically organized and synced from LeetCode. Structured by topic, "
                  "with per-problem notes on approach and complexity.\n")
    lines.append("## Progress\n")
    lines.append(f"**Total Solved: {total}**\n")
    lines.append("| Difficulty | Count |")
    lines.append("|---|---|")
    lines.append(f"| 🟢 Easy | {easy} |")
    lines.append(f"| 🟡 Medium | {medium} |")
    lines.append(f"| 🔴 Hard | {hard} |\n")

    lines.append("## Topics\n")
    lines.append("| Topic | Solved | Problems |")
    lines.append("|---|---|---|")
    for topic in sorted(by_topic.keys()):
        problems = by_topic[topic]
        links = ", ".join(
            f"[{pid}]({'topics/' + topic + '/' + slug + '/'})"
            for pid, title, slug in sorted(problems, key=lambda x: int(x[0]))
        )
        topic_display = topic.replace("-", " ").title()
        lines.append(f"| {topic_display} | {len(problems)} | {links} |")

    lines.append("\n---")
    lines.append(" This README is auto-generated by `scripts/organize.py` via a scheduled GitHub Action. ")

    (REPO_ROOT / "README.md").write_text("\n".join(lines))
    print("Root README.md rebuilt.")


if __name__ == "__main__":
    cache = organize()
    build_root_readme(cache)