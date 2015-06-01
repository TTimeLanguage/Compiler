int main() {
	date d = 2015.06.01;
	time t = 19:19:19;

	d++;
	write(d);
	lf();

	d += 0.0.1;
	write(d);
	lf();

	d -= 0.0.2;
	write(d);
	lf();

	t++;
	write(t);
	lf();

	t += 0:1:0;
	write(t);
	lf();
}