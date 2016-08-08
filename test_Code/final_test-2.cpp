int array[100] = { 0 };

int fibonacci(int n) {
	if (n < 3) {
		return 1;
	}
	
	return fibonacci(n - 1) + fibonacci(n - 2);
}

int main() {
	int n, i = 1;

	read(&n);

	for (; i <= n; i++) {
		array[i] = fibonacci(i);
	}

	for (i = 1; i <= n; i++) {
		write(array[i]);
	}
}