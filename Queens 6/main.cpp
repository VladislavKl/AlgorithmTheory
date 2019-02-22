#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream>


const int OBSTACLE = 2;

unsigned int n, m, k;

std::ifstream fin("input.txt");
std::ofstream fout("output.txt");

std::vector<std::vector<int> > chessboard;
std::vector<std::vector<int> > a;
unsigned int emptyCellsX[100];
unsigned int emptyCellsY[100];

unsigned int xStart, yStart;
unsigned int dx, dy;
int numberOfEmptyCells;


inline void add() {
    xStart += dx;
    yStart += dy;

    while (true) {
        if (xStart >= n || yStart >= m || chessboard[xStart][yStart] == OBSTACLE) {
            return;
        }
        a[xStart][yStart] += 1;
        xStart += dx;
        yStart += dy;
    }
}



inline bool refresh(unsigned int mask) {
    for (unsigned int i = 0; i < numberOfEmptyCells; ++ i)
        a[emptyCellsX[i]][emptyCellsY[i]] = 0;
    for (unsigned int k = 0; k < numberOfEmptyCells; ++ k) {
        int i = emptyCellsX[k];
        int j = emptyCellsY[k];
        if (mask & (1 << k)) {
            xStart = i;
            yStart = j;
            dx = 1;
            dy = 0;

            add();
            xStart = i;
            yStart = j;
            dx = 1;
            dy = 1;

            add();
            xStart = i;
            yStart = j;
            dx = 0;
            dy = 1;

            add();
            xStart = i;
            yStart = j;
            dx = - 1;
            dy = 1;


            add();
            xStart = i;
            yStart = j;
            dx = - 1;
            dy = 0;

            add();
            xStart = i;
            yStart = j;
            dx = - 1;
            dy = - 1;

            add();
            xStart = i;
            yStart = j;
            dx = 0;
            dy = - 1;

            add();
            xStart = i;
            yStart = j;
            dx = 1;
            dy = - 1;
            add();

            ++ a[i][j];
        }
    }
    for (unsigned int i = 0; i < numberOfEmptyCells; ++ i)
        if (a[emptyCellsX[i]][emptyCellsY[i]] < k)
            return false;
    return true;
}

inline int qOfBits (unsigned int n) {
    int res = 0;
    for (; n; n >>= 1) {
        res += n & 1;
    }
    return res;
}

inline void print(unsigned int x) {
    std::vector<std::pair<unsigned int, unsigned int> > res;
    for (int i = 0; i < numberOfEmptyCells; ++ i) {
        if (x & (1 << i)) {
            res.emplace_back(std::make_pair(emptyCellsX[i], emptyCellsY[i]));
        }
    }
    for (unsigned int i = 0; i < res.size(); ++ i) {
        fout << res[i].first << " ";
        if (i != res.size() - 1) {
            fout << res[i].second << " ";
        } else {
            fout << res[i].second;
        }
    }
    fout << "\n";
}

int main() {
    std::ios_base::sync_with_stdio(false);


    int numberOfObstacles;

    fin >> k >> n >> m >> numberOfObstacles;

    chessboard = std::vector<std::vector<int> >(n, std::vector<int>(m, 0));
    std::vector<int> answer[100];
    a = chessboard;

    std::vector<int> obstacles;
    int x, y;
    for (int i = 0; i < numberOfObstacles; ++ i) {
        fin >> x >> y;
        chessboard[x][y] = OBSTACLE;
        obstacles.push_back(x * m + y);
    }

    numberOfEmptyCells = 0;
    for (int i = 0; i < n; ++ i)
        for (int j = 0; j < m; ++ j)
            if (chessboard[i][j] != OBSTACLE) {
                emptyCellsX[numberOfEmptyCells] = i;
                emptyCellsY[numberOfEmptyCells] = j;
                numberOfEmptyCells += 1;
            }


    int minNumberOfBitsInTheSolution = 100;

    for (unsigned int mask = 0; mask < (1 << numberOfEmptyCells); ++ mask) {
        int numberOfBits = qOfBits(mask);
        if (numberOfBits < k)
            continue;
        if (numberOfBits <= minNumberOfBitsInTheSolution) {
            if (refresh(mask)) {
                answer[numberOfBits].push_back(mask);
                if (numberOfBits < minNumberOfBitsInTheSolution)
                    minNumberOfBitsInTheSolution = numberOfBits;
            }
        }
    }

    for (int i = 0; i < 100; ++ i) {
        if (answer[i].size() != 0) {
            fout << answer[i].size() << "\n";
            for (int j = 0; j < answer[i].size(); ++ j) {
                print(answer[i][j]);
            }
            return 0;
        }
    }
    std::cout << 0 << "\n";

    return 0;
}
