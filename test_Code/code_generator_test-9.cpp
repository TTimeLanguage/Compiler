int main() {
	time t = 0:0:0;

	t += 0:1:0;

	write(t);
	lf();

	t *= 10;
	write(t);
	lf();

	t *= 10;
	write(t);
	lf();

	write(1:0:0 * 24  - 0:0:1);
	lf();
}