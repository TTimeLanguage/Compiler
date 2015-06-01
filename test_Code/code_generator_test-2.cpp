int main() {
	int n = 0;
	int s, t[10] = { 0, 1,2,3,4,5,6,7,8,9};
	float f = 1.1;
	time ti = 1:1:1;
	date d = 2015.05.29;

	for	(n = 0; n < 10; n++) {
		write(t[n]);
	}
	lf();

	f = f + 0.9;
	n = (int) f;
	write(n);
	lf();

	write(f);
	lf();

	write('c');
	lf();

	ti++;
	ti += 0:1:0;
	write(ti);
	lf();

	write(23:59:1 + 0:0:59);
	lf();

	d++;
	write(d);
	lf();

	s = t[2];
	t[3] += s;
	for (n = 0; n < 10; n++) {
		if (n % 2 == 1 && n % 3 == 0) {
			write(n);
			continue;
		} else if (n == 9) {
			break;
		} else {
			s++;
		}
	}
	t[t[9]] = s;
}

int print() {
	return 1;
}