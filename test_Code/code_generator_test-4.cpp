int fi(int n) {
	if (n < 3) {
		return 1;
	}
	
	return fi(n - 1) + fi(n - 2);
}

int main() {
	int n;
	int i;

	read(&n);

	for (i = 1; i <= n; i++) {
		write(fi(i))
	}
}