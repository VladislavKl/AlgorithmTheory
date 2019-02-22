#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#include <limits.h>
#include <opencl-c.h>

#define MAX 300001
using namespace std;

int *tree;
int *a;

int min(int x, int y)
{
	return x < y ? x : y;
}

void build_tree(int v, int tl, int tr) {
	if (tl == tr) {
		tree[v] = a[tl];
	}
	else {
		int tm = (tl + tr) / 2;
		build_tree(v * 2, tl, tm);
		build_tree(v * 2 + 1, tm + 1, tr);
		tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
	}
}

int get_min(int l, int r, int v, int tl, int tr) {

	if (l <= tl && tr <= r) {
		return tree[v];
	}

	if (tr < l || r < tl) {
		return INT_MAX;
	}

	int tm = (tl + tr) / 2;
	return min(get_min(l, r, v * 2, tl, tm),
		get_min(l, r, v * 2 + 1, tm + 1, tr));
}

void update(int idx, int val, int v, int tl, int tr) {

	if (idx <= tl && tr <= idx) {
		a[idx] = val;
		tree[v] = val;
		return;
	}

	if (tr < idx || idx < tl) {
		return;
	}

	int tm = (tl + tr) / 2;
	update(idx, val, v * 2, tl, tm);
	update(idx, val, v * 2 + 1, tm + 1, tr);
	tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
}

struct Team {
	int x;
	int y;
	int z;

	Team& operator =(const Team &t)
	{
		if (&t != this)
		{
			x = t.x;
			y = t.y;
			z = t.z;
		}
		return *this;
	}
};

bool sortByX(const Team& t1, const Team& t2)
{
	return t1.x  < t2.x;
}


int main() {

	FILE* finp = fopen("input.txt", "r");
	ofstream fout("output.txt");

	int n = 0;
	fscanf(finp, "%d", &n);
	int N = 3 * n;

	vector <Team> team(n);
	tree = new int[N * 4];
	a = new int[N];

	for (int i = 0; i < N; i++)
	{
		a[i] = MAX;
	}

	for (int i = 0; i < n; i++)
	{
		fscanf(finp, "%d %d %d", &team[i].x, &team[i].y, &team[i].z);
	}

	stable_sort(team.begin(), team.end(), sortByX);
	a[team[0].y - 1] = team[0].z;

	build_tree(1, 0, N - 1);

	int z = 0, y = 0, count = 1;
	for (int i = 1; i < n; i++)
	{
		y = team[i].y - 1;
		z = team[i].z;
		if (get_min(0, y, 1, 0, N - 1) > z)
			count++;
		update(y, z, 1, 0, N - 1);
	}

	delete[] tree;
	delete[] a;
	fout << count;
	return 0;
}