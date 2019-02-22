#pragma comment(linker, "/STACK:268435456")
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>


const int CONNECTED = -1, NOEDGES = -2, STOP = -3;

void connectivityComponentToSequence(int&, const int&,  std::vector<char>&, std::vector<std::vector<std::pair<int, int> > >&, std::vector<int>&);

int dfs(int& currentVertex, const int& previousVertex,  std::vector<char>& visited, std::vector<char>& connected,
        std::vector<std::vector<std::pair<int, int>>>& adjacencyList, std::vector<int>& sequence, std::queue<int>& CCcomponentVertexes) {

    visited[currentVertex] = true;

    for (auto adjacentVertex: adjacencyList[currentVertex]) {
        if (adjacentVertex.second != previousVertex) {
            if (visited[adjacentVertex.first]) {

                sequence[adjacentVertex.second] = adjacentVertex.first;
                CCcomponentVertexes.push(currentVertex);

                return adjacentVertex.first;
            }

            int tempVertex = dfs(adjacentVertex.first, adjacentVertex.second, visited, connected, adjacencyList,
                                 sequence, CCcomponentVertexes);
            //if (tempVertex == STOP)
            //    return STOP;

            if (!(tempVertex == NOEDGES | tempVertex == CONNECTED)) {

                sequence[adjacentVertex.second] = adjacentVertex.first;
                connected[currentVertex] = true;
                CCcomponentVertexes.push(currentVertex);

                if (tempVertex != currentVertex) {
                    return tempVertex;
                } else {
                    while (!CCcomponentVertexes.empty()) {
                        connectivityComponentToSequence(CCcomponentVertexes.front(), NOEDGES, connected, adjacencyList,
                                                        sequence);
                        CCcomponentVertexes.pop();
                    }
                    return CONNECTED;
                }
            }

            if (tempVertex == CONNECTED) {
                return CONNECTED;
            }

        }
    }

    if (previousVertex == NOEDGES) {
        std::cout << "No";
        exit(0);
        //return STOP;
    }

    return NOEDGES;
}

void connectivityComponentToSequence(int& currentVertex, const int& previousVertex,  std::vector<char>& connected,
                                     std::vector<std::vector<std::pair<int, int> > >& adjacencyList, std::vector<int>& sequence) {

    connected[currentVertex] = true;
    for (auto adjacentVertex: adjacencyList[currentVertex]) {
        if (adjacentVertex.second != previousVertex && !connected[adjacentVertex.first]) {
            sequence[adjacentVertex.second] = adjacentVertex.first;
            connectivityComponentToSequence(adjacentVertex.first, adjacentVertex.second, connected, adjacencyList,
                                            sequence);
        }

        if (connected[adjacentVertex.first] && sequence[adjacentVertex.second] == NOEDGES) {
            sequence[adjacentVertex.second] = adjacentVertex.first;
        }
    }
}

int main() {
    std::ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    unsigned int r, c;
    std::cin >> r >> c;
    std::vector<std::vector<std::pair<int, int> > > adjacencyList(r);

    std::vector<int> sequence(c, NOEDGES);

    std::vector<char> visited(r, false);
    std::vector<char> connected(r, false);

    int i, from, to;
    for (i = 0; i < c; ++i) {
        std::cin >> from >> to;
        adjacencyList[--from].push_back(std::make_pair(--to, i));
        adjacencyList[to].push_back(std::make_pair(from, i));
    }

    std::queue<int> CCcomponentVertexes;

    for (i = 0; i < r; ++i) {
        if (!(visited[i] | connected[i])) {
            //if (dfs(i, NOEDGES, visited, connected, adjacencyList, sequence, CCcomponentVertexes) == STOP)
            // return 0;
            dfs(i, NOEDGES, visited, connected, adjacencyList, sequence, CCcomponentVertexes);
        }
    }

    for (i = 0; i < c - 1; ++i) {
        std::cout << std::to_string(sequence[i] + 1) + " ";
    }
    std::cout << std::to_string(sequence[c - 1] + 1);

    return 0;
}
