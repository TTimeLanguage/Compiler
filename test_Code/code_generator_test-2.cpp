int main() {
	int n = 0;
	int s, t[10] = { 0, 1,2,3,4,5,6,7,8,9};

	s = t[2];
	t[3] += s;
	for (n = 0; n < 10; n++) {
		if (n % 2 == 1) {
			continue;
		} else if (n == 9) {
			break;
		} else {
			s++;
		}
	}
}