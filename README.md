# 🧩 MazeSolver

**A Java maze solver that finds a path from start to finish using Depth-First Search (DFS) and Breadth-First Search (BFS).**

![Java](https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white)
![Algorithms](https://img.shields.io/badge/Algorithms-DFS%20%7C%20BFS-blueviolet)
![Status](https://img.shields.io/badge/Status-Completed-success)

---

## 📖 Overview

MazeSolver models a maze as a grid and solves it using two classic graph-search algorithms. Running both on the same maze makes it easy to compare how they explore the search space and what kind of paths they produce. It is a hands-on project on graph traversal, one of the core foundations of search problems in AI.

## ✨ Features

- 🗺️ Maze represented as a 2D grid with walls, open paths, a start point and an end point
- 🔎 Solves the maze using **DFS**
- 🌊 Solves the maze using **BFS**
- 🛣️ Finds and displays the path from start to end
- 📊 Lets you compare the behavior of both algorithms on the same maze

## 🧠 Algorithms Used

### Depth-First Search (DFS)
Explores as far as possible along one path before backtracking. It uses a stack (or recursion) and finds *a* path, but not necessarily the shortest one.

### Breadth-First Search (BFS)
Explores the maze level by level using a queue. It always finds the **shortest path** in an unweighted grid.

| | DFS | BFS |
|---|---|---|
| Data structure | Stack / Recursion | Queue |
| Finds shortest path | ❌ Not guaranteed | ✅ Yes |
| Time complexity | O(V + E) | O(V + E) |
| Memory usage | Lower in most mazes | Higher (stores whole frontier) |

*(V = number of cells, E = number of connections between neighbouring cells)*

## 🏗️ Tech Stack

- **Language:** Java
- **Concepts:** Graph traversal, stacks, queues, recursion, backtracking
- **Version Control:** Git, GitHub

## ⚙️ How to Run

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) 8 or higher

### 1. Clone the repository

```bash
git clone https://github.com/rabeyaasif-aiml/MazeSolver.git
cd MazeSolver
```

### 2. Compile

```bash
javac *.java
```

### 3. Run

```bash
java Main
```

## 📁 Project Structure

```
MazeSolver/
├── Main.java
├── Maze.java
├── DFS.java
├── BFS.java
├── .gitignore
└── README.md
```

## 🔮 Future Improvements

- Graphical visualization of the search process
- A* search for faster pathfinding
- Random maze generation
- Step count and path length comparison between algorithms

## 👩🏻‍💻 Author

**Rabeya Asif**
Software Engineering student at IOBM

- GitHub: [@rabeyaasif-aiml](https://github.com/rabeyaasif-aiml)
- LinkedIn: [rabeya-asif-ml](https://linkedin.com/in/rabeya-asif-ml)

---

⭐ If you found this project interesting, feel free to star the repo!
