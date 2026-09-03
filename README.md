# 📚 LeetCode Solutions

[![LeetCode](https://img.shields.io/badge/LeetCode-Profile-FFA116?style=for-the-badge&logo=leetcode&logoColor=white)](https://leetcode.com/shivamm-shukla)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Auto Synced](https://img.shields.io/badge/Sync-Automated-brightgreen?style=for-the-badge&logo=github-actions&logoColor=white)](https://github.com/features/actions)
![Last Commit](https://img.shields.io/github/last-commit/shivamm-shukla/leetcode-solutions?style=for-the-badge&color=blue)

Data structures and algorithms problems I solve on LeetCode, written in Java. Everything is filed topic-wise, and every problem carries a short note on the approach I took along with its time and space complexity.

Solutions reach this repo on their own — I don't move folders around by hand.

---

## 📊 Progress

<!-- PROGRESS:START -->
**Total solved: 0**

| Difficulty | Solved |
|---|---|
| 🟢 Easy | 0 |
| 🟡 Medium | 0 |
| 🔴 Hard | 0 |
<!-- PROGRESS:END -->

---

## 🗂️ Topics

<!-- TOPICS:START -->
| Topic | Solved | Problems |
|---|---|---|
| _Nothing synced yet_ | 0 | |
<!-- TOPICS:END -->

---

## ⚙️ How this repo works

1. I solve a problem on [LeetCode](https://leetcode.com/shivamm-shukla) in Java.
2. On **Accepted**, [LeetHub v2](https://github.com/arunbhardwaj/LeetHub-2.0) commits the solution straight to this repo.
3. A [GitHub Action](.github/workflows/organize.yml) picks it up, files it under `topics/<tag>/<problem>/`, writes the problem README, and refreshes the two tables above.

Everything on this page is mine except the blocks between the `PROGRESS` and `TOPICS` markers — those two are generated, and the script touches nothing else.

## 📁 Layout

```
topics/
└── array/
    └── two-sum/
        ├── README.md      # the problem, my approach, complexity
        └── Solution.java  # the accepted submission
scripts/
└── organize.py            # files solutions, rebuilds the tables
.github/workflows/
└── organize.yml           # runs the organizer on every push
```

Problems are filed under their primary LeetCode tag, so a problem tagged `Array, Hash Table` lands in `topics/array/`.

## 🛠️ Tooling

| | |
|---|---|
| **Language** | Java |
| **Sync** | LeetHub v2 (Chrome extension) |
| **Automation** | GitHub Actions + Python 3.11 |
| **Metadata** | LeetCode public GraphQL API |

---

<sub>Organizer: <a href="scripts/organize.py"><code>scripts/organize.py</code></a> — run it locally any time with <code>python scripts/organize.py</code>.</sub>
