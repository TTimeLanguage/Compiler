void f(int d) {
	write(d);
	lf();

	d += 1;
	write(d);
	lf();

	return;
}

int main() {
	int d = 1;

	f(3);

	write(d);
	lf();

	d += 1;
	write(d);
	lf();
}