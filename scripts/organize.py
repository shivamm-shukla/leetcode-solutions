#!/usr/bin/env python3
"""
Files LeetCode solutions pushed by LeetHub into a topic-wise structure and
keeps the progress tables in README.md up to date.

    python scripts/organize.py

The only environment variable it reads is REPO_ROOT (defaults to ".").

Two rules this script never breaks:
  * It only ever rewrites text between <!-- NAME:START --> / <!-- NAME:END -->
    markers, so hand-written prose is safe.
  * It never overwrites a problem README that already exists — approach notes
    survive re-submissions.
"""

from __future__ import annotations

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
CACHE_FILE = REPO_ROOT / ".leetcode_meta_cache.json"
ROOT_README = REPO_ROOT / "README.md"

LEETCODE_GRAPHQL = "https://leetcode.com/graphql"
REQUEST_DELAY = 0.5  # be polite to the public endpoint

# LeetHub names folders "0001-two-sum". A directory that doesn't start with a
# problem number is one of ours, not a solution drop.
PROBLEM_DIR_RE = re.compile(r"^(\d+)[-._\s]+(.+)$")

IGNORE_DIRS = {".git", ".github", "topics", "scripts", "node_modules"}

SOLUTION_SUFFIXES = {
    ".java", ".py", ".cpp", ".cc", ".c", ".cs", ".js", ".ts",
    ".go", ".rs", ".kt", ".rb", ".swift", ".scala", ".sql",
}

DIFFICULTY_ICON = {"Easy": "🟢", "Medium": "🟡", "Hard": "🔴"}


# --------------------------------------------------------------------------
# marker-aware text patching
# --------------------------------------------------------------------------

def replace_block(text: str, name: str, body: str):
    """Swap the contents of a marker block. Returns None if it isn't there."""
    start, end = f"<!-- {name}:START -->", f"<!-- {name}:END -->"
    pattern = re.compile(re.escape(start) + r".*?" + re.escape(end), re.DOTALL)
    if not pattern.search(text):
        return None
    return pattern.sub(lambda _: f"{start}\n{body}\n{end}", text, count=1)


def wrap_block(name: str, body: str) -> str:
    return f"<!-- {name}:START -->\n{body}\n<!-- {name}:END -->"


# --------------------------------------------------------------------------
# metadata
# --------------------------------------------------------------------------

def load_cache() -> dict:
    if not CACHE_FILE.exists():
        return {}
    try:
        return json.loads(CACHE_FILE.read_text() or "{}")
    except json.JSONDecodeError:
        print("! metadata cache was unreadable, rebuilding it")
        return {}


def save_cache(cache: dict) -> None:
    CACHE_FILE.write_text(json.dumps(cache, indent=2, ensure_ascii=False, sort_keys=True) + "\n")


def fetch_problem_meta(slug: str):
    payload = {
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
    request = urllib.request.Request(
        LEETCODE_GRAPHQL,
        data=json.dumps(payload).encode(),
        headers={
            "Content-Type": "application/json",
            "User-Agent": "leetcode-solutions-organizer",
        },
    )
    try:
        with urllib.request.urlopen(request, timeout=15) as response:
            data = json.loads(response.read())
    except Exception as exc:
        print(f"  ! could not reach LeetCode for '{slug}': {exc}")
        return None

    question = (data.get("data") or {}).get("question")
    if not question:
        print(f"  ! LeetCode has no problem called '{slug}'")
        return None

    return {
        "id": question["questionFrontendId"],
        "title": question["title"],
        "difficulty": question["difficulty"],
        "topics": [
            {"name": tag["name"], "slug": tag["slug"]}
            for tag in question.get("topicTags") or []
        ],
    }


REQUIRED_META_KEYS = {"id", "title", "difficulty", "topics"}


def get_meta(slug: str, cache: dict):
    """Cached lookup. The cache is a speed-up only, never a source of truth."""
    cached = cache.get(slug)
    if cached and REQUIRED_META_KEYS <= set(cached):
        return cached

    meta = fetch_problem_meta(slug)
    time.sleep(REQUEST_DELAY)
    if meta:
        cache[slug] = meta
    return meta


def primary_topic(meta: dict) -> str:
    topics = meta.get("topics") or []
    return topics[0]["slug"] if topics else "misc"


# --------------------------------------------------------------------------
# moving solution folders into topics/
# --------------------------------------------------------------------------

def find_pending_problem_dirs():
    """Solution folders sitting at the repo root, waiting to be filed."""
    pending = []
    for entry in sorted(REPO_ROOT.iterdir()):
        if not entry.is_dir() or entry.name.startswith(".") or entry.name in IGNORE_DIRS:
            continue
        match = PROBLEM_DIR_RE.match(entry.name)
        if not match:
            continue
        pending.append((entry, match.group(2).strip("-._ ").lower()))
    return pending


def move_contents(src: Path, dest: Path) -> None:
    """Move src into dest, letting a newer solution win but keeping notes."""
    dest.mkdir(parents=True, exist_ok=True)
    for item in sorted(src.iterdir()):
        target = dest / item.name

        if item.name == "README.md" and target.exists():
            item.unlink()  # my notes already live there
            continue

        if target.exists():
            shutil.rmtree(target) if target.is_dir() else target.unlink()

        shutil.move(str(item), str(target))

    shutil.rmtree(src)


def find_solution_file(problem_dir: Path):
    candidates = [
        f for f in sorted(problem_dir.iterdir())
        if f.is_file() and f.suffix.lower() in SOLUTION_SUFFIXES
    ]
    return candidates[0] if candidates else None


# --------------------------------------------------------------------------
# per-problem README
# --------------------------------------------------------------------------

NOTES_TEMPLATE = """## Approach

_Pending._

## Complexity

- **Time:** _pending_
- **Space:** _pending_
"""


def problem_meta_block(slug: str, meta: dict, solution: Path | None) -> str:
    tags = ", ".join(tag["name"] for tag in meta.get("topics") or []) or "—"
    lines = [
        f"**Difficulty:** {DIFFICULTY_ICON.get(meta['difficulty'], '')} {meta['difficulty']}",
        f"**Topics:** {tags}",
        f"**Problem:** [leetcode.com/problems/{slug}](https://leetcode.com/problems/{slug}/)",
    ]
    if solution is not None:
        lines.append(f"**Solution:** [`{solution.name}`](./{solution.name})")
    return "  \n".join(lines)  # two trailing spaces = a real line break


def ensure_problem_readme(problem_dir: Path, slug: str, meta: dict) -> None:
    readme = problem_dir / "README.md"
    block = problem_meta_block(slug, meta, find_solution_file(problem_dir))

    if not readme.exists():
        readme.write_text(
            f"# {meta['id']}. {meta['title']}\n\n"
            f"{wrap_block('PROBLEM', block)}\n\n"
            f"{NOTES_TEMPLATE}"
        )
        return

    existing = readme.read_text()

    patched = replace_block(existing, "PROBLEM", block)
    if patched is None:
        # A README LeetHub wrote, or one I started by hand. Slot the metadata in
        # under the first heading rather than on top of whatever is there.
        lines = existing.splitlines()
        insert_at = 1 if lines and lines[0].startswith("#") else 0
        lines[insert_at:insert_at] = ["", wrap_block("PROBLEM", block)]
        patched = "\n".join(lines).rstrip() + "\n"

    if "## Approach" not in patched:
        patched = patched.rstrip() + "\n\n" + NOTES_TEMPLATE

    if patched != existing:
        readme.write_text(patched)


# --------------------------------------------------------------------------
# root README tables
# --------------------------------------------------------------------------

def collect_solved(cache: dict):
    """Walk topics/ — the filesystem is the source of truth for the counts."""
    solved = []
    if not TOPICS_DIR.exists():
        return solved

    for topic_dir in sorted(TOPICS_DIR.iterdir()):
        if not topic_dir.is_dir():
            continue
        for problem_dir in sorted(topic_dir.iterdir()):
            if not problem_dir.is_dir():
                continue
            meta = get_meta(problem_dir.name, cache)
            if not meta:
                continue
            ensure_problem_readme(problem_dir, problem_dir.name, meta)
            solved.append((topic_dir.name, problem_dir.name, meta))
    return solved


def build_progress_block(solved) -> str:
    counts = defaultdict(int)
    for _, _, meta in solved:
        counts[meta["difficulty"]] += 1

    lines = [
        f"**Total solved: {len(solved)}**",
        "",
        "| Difficulty | Solved |",
        "|---|---|",
    ]
    for difficulty in ("Easy", "Medium", "Hard"):
        lines.append(f"| {DIFFICULTY_ICON[difficulty]} {difficulty} | {counts[difficulty]} |")
    return "\n".join(lines)


def build_topics_block(solved) -> str:
    by_topic = defaultdict(list)
    for topic, slug, meta in solved:
        by_topic[topic].append((int(meta["id"]), meta["title"], slug))

    lines = ["| Topic | Solved | Problems |", "|---|---|---|"]

    if not by_topic:
        lines.append("| _Nothing synced yet_ | 0 | |")
        return "\n".join(lines)

    for topic in sorted(by_topic, key=lambda t: (-len(by_topic[t]), t)):
        problems = sorted(by_topic[topic])
        links = ", ".join(
            f'[{pid}](topics/{topic}/{slug}/ "{title}")' for pid, title, slug in problems
        )
        lines.append(f"| {topic.replace('-', ' ').title()} | {len(problems)} | {links} |")

    return "\n".join(lines)


# LeetHub appends its own topic index to the root README on every submission.
# Its links hardcode /tree/master/ and point at the flat folders it drops at the
# root, so once solutions are filed under topics/ every one of them 404s. The
# tables above cover the same ground, so this section gets dropped.
LEETHUB_TOPICS_RE = re.compile(
    r"\n*<!---LeetCode Topics Start-->.*?<!---LeetCode Topics End-->",
    re.DOTALL,
)


def strip_leethub_topics(text: str) -> str:
    stripped, count = LEETHUB_TOPICS_RE.subn("", text)
    if count:
        print("Removed LeetHub's own topic index from README.md.")
    return stripped.rstrip() + "\n"


def update_root_readme(solved) -> None:
    if not ROOT_README.exists():
        print("! README.md is missing, skipping the progress tables")
        return

    original = ROOT_README.read_text()
    text = strip_leethub_topics(original)

    for name, body in (
        ("PROGRESS", build_progress_block(solved)),
        ("TOPICS", build_topics_block(solved)),
    ):
        patched = replace_block(text, name, body)
        if patched is None:
            print(f"! no <!-- {name}:START --> marker in README.md, leaving it alone")
            continue
        text = patched

    if text != original:
        ROOT_README.write_text(text)
        print("README.md tables updated.")
    else:
        print("README.md already up to date.")


# --------------------------------------------------------------------------

def main() -> None:
    cache = load_cache()
    TOPICS_DIR.mkdir(exist_ok=True)

    pending = find_pending_problem_dirs()
    print(f"{len(pending)} solution folder(s) waiting at the repo root.")

    filed = 0
    for src, slug in pending:
        meta = get_meta(slug, cache)
        if not meta:
            print(f"  - leaving '{src.name}' where it is, will retry next run")
            continue

        topic = primary_topic(meta)
        dest = TOPICS_DIR / topic / slug
        move_contents(src, dest)
        ensure_problem_readme(dest, slug, meta)
        print(f"  - {src.name} -> topics/{topic}/{slug}/")
        filed += 1

    solved = collect_solved(cache)

    # Drop metadata for problems that are no longer in the repo.
    live_slugs = {slug for _, slug, _ in solved} | {slug for _, slug in pending}
    cache = {slug: meta for slug, meta in cache.items() if slug in live_slugs}
    save_cache(cache)

    update_root_readme(solved)
    print(f"Filed {filed} new solution(s). {len(solved)} problem(s) tracked in total.")


if __name__ == "__main__":
    main()
