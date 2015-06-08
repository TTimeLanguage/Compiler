int main() {
	date d = 2012.02.29;
	time t = 19:19:19;

	d++;
	write(d);
	lf();

	d += daysToDate(1);
	write(d);
	lf();

	d -= daysToDate(2);
	write(d);
	lf();

	t++;
	write(t);
	lf();

	t += 0:1:0;
	write(t);
	lf();
}