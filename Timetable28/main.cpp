#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <ctime>

long long max(long long a, long long b) {
    return (a > b ? a : b);
}

class Help{
public:
    int number;
    long long deadline;

    Help(int n, long long d):number(n), deadline(d){}

    bool operator < (const Help& o) const{
        return o.deadline < deadline;
    }
};

int main() {
    std::ifstream fin("input.txt");
    int m, n;

    fin >> n;//quantity of jobs

    std::vector<int> sequence(n, 0);//contains optimal sequence
    std::vector<long long> processTimes(n , 0ll);//contains process time for each job
    std::vector<long long> deadlines(n, 0ll);//contains deadline for each job

    //getting process time and deadline for each job
    for (int i = 0; i < n; ++i) {
        fin >> processTimes[i];
        fin >> deadlines[i];
    }

    fin >> m;//quantity of job orders

    std::set<int> adjacencyMatrix[n];//contains job orders
    std::set<int> indexesOfNotEmpty;//contains indexes of jobs after which go other jobs

    int from, to;
    for (int i = 0; i < m; ++i) {
        fin >> from;
        fin >> to;
        adjacencyMatrix[--from].insert(--to);
        indexesOfNotEmpty.insert(from);
    }

    fin.close();

    int place = 0;

    std::set<Help> candidatesForSequence; //sorted by decreasing deadline, contains number of job and its deadline

    for (int i = 0; i < n; ++i)
        if (adjacencyMatrix[i].empty())
            candidatesForSequence.insert(Help(i, deadlines[i]));

    std::vector<int> indexesOfNewEmpty;

    while (place < n) {

        //get the job which we'll be done (from last to the first)
        sequence[place] = candidatesForSequence.begin()->number;
        candidatesForSequence.erase(candidatesForSequence.begin());

        //we have deleted job and now we have to delete it from adjacencyMatrix
        for (auto it:indexesOfNotEmpty) {
            adjacencyMatrix[it].erase(sequence[place]);
            //if all jobs after it are done, it becomes the candidate
            if (adjacencyMatrix[it].empty()) {
                candidatesForSequence.insert(Help(it, deadlines[it]));
                indexesOfNewEmpty.push_back(it); //so that it's empty we don't have to delete any connections from adjacencyMatrix anymore
            }
        }
        
        for (auto it : indexesOfNewEmpty) //-----||-----
            indexesOfNotEmpty.erase(it);
        
        indexesOfNewEmpty.clear();

        ++place; //iterates to the next member of sequence
    }

    long long c = 0ll, maximum = -1ll;
    int maximumIndex = -1;
    long long t;

    //finds job with max fee and its index from the optimal sequence
    for (int i = --place; i >= 0; --i) {
        c += processTimes[sequence[i]];
        t = max(c - deadlines[sequence[i]], 0);
        if (t >= maximum) {
            maximum = t;
            maximumIndex = sequence[i];
        }
    }

    std::ofstream fout("output.txt");

    //outputs job's index and max fee
    fout << ++maximumIndex << " " << maximum << std::endl;

    //outputs optimal sequence
    for (int i = place; i > 0; --i)
        fout << ++sequence[i] << std::endl;
    fout << ++sequence[0];

    fout.close();
    return 0;
}