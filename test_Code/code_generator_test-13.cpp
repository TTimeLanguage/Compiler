int test(int a, int b, int c) {
	return a + b + c;
}

int main() {
	int a, b, c;

	read(&a);
	read(&b);
	read(&c);

	a = (b = c + 1) + 3;
	write(a);
}