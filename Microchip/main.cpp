#include <iostream>
#include <vector>

using namespace std;

const int N = 39, A = 31;
const int UP = 0;
const int RIGHT = 1;
const int DOWN = 2;
const int LEFT = 3;

int n, a;
vector<int> rows[A], columns[A];
bool field[A][A];
int dp[A][A][A][A][16];
bool is[A][A][A][A][16];
int answerDirection[A][A];



int blockDir(int flag, int dir) {
    if (dir == UP) {
        return flag + 1 >= 2 ? flag : flag + 1;
    }
    if (dir == DOWN) {
        return flag + 4 >= 8 ? flag : flag + 4;
    }
    if (dir == LEFT) {
        return flag + 8 >= 16 ? flag : flag + 8;
    }
    if (dir == RIGHT) {
        return flag + 2 >= 4 ? flag : flag + 2;
    }
}


bool isBlocked(int flag, int dir) {
    if (dir == UP) {
        return flag % 2;
    }
    if (dir == DOWN) {
        return flag / 4 % 2;
    }
    if (dir == LEFT) {
        return flag / 8 % 2;
    }
    if (dir == RIGHT) {
        return flag / 2 % 2;
    }
}


bool canDrawLine(int r, int c, int flag, int direction) {
    if (isBlocked(flag, direction))
        return false;
    if (direction == UP) {
        return columns[c].front() == r;
    } else if (direction == DOWN) {
        return columns[c].back() == r;
    } else if (direction == LEFT) {
        return rows[r].front() == c;
    } else {
        return rows[r].back() == c;
    }
}

void availableDirections(){

}


int solution(int left, int top, int bottom, int topAllowed, int bottomAllowed) {
    if (rows[left].size() == 0)
        solution(left + 1, top, bottom, topAllowed, bottomAllowed);
    if (columns[bottom].size() == 0)
        solution(left, top, bottom - 1, topAllowed, bottomAllowed);
    if (columns[top].size() == 0)
        solution(left, top + 1, bottom, topAllowed, bottomAllowed);

    if (rows[left].size() == 1){
        solution(left + 1, top, bottom, topAllowed, bottomAllowed);
        solution(left, top + 1, bottom, topAllowed + 1, bottomAllowed);
        solution(left, top, bottom, topAllowed, bottomAllowed);
        solution(left + 1, top, bottom, topAllowed, bottomAllowed);

    }




}

void restoreSolution(int left, int top, int bottom, int topAllowed, int bottomAllowed) {

}

int normalize(int direction) {
    return (direction + 3) % 4;
}


int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    cin >> a >> n;

    vector<pair<int, int>> points(n);
    for (int i = 0; i < n; i++) {
        int x, y;
        cin >> x >> y;
        points[i] = {x, y};
        field[x][y] = true;
        rows[x].push_back(y);
        columns[y].push_back(x);
    }

    for (int i = 0; i < A; i++) {
        sort(rows[i].begin(), rows[i].end());
        sort(columns[i].begin(), columns[i].end());
    }
    cout << solve(1, 1, a - 1, a - 1) << endl;
    restoreSolve(1, 1, a - 1, a - 1);
    for (auto i : points) {
        int x = i.first, y = i.second;
        switch (normalize(answerDirection[x][y])) {
            case UP:
                cout << "UP" << endl;
                break;
            case DOWN:
                cout << "DOWN" << endl;
                break;
            case LEFT:
                cout << "LEFT" << endl;
                break;
            case RIGHT:
                cout << "RIGHT" << endl;
                break;
            default:
                cout << "ERROR" << endl;
        }
    }
}