int main() {
	int n = 0;
	int s, t[10] = { 0, 1,2,3,4,5,6,7,8,9};
	time ti = 1:1:1;
	date d = 2015.05.29;

	for	(n = 0; n < 10; n++) {
		write(t[n]);
	}
	lf();

	ti++;
	ti += 0:1:0;
	write(ti);
	lf();

	write(0:60:0 - 0:0:1);
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