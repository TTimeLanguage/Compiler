int main() {
	int i = 0;

	continue;
	for (; i < 10; i++) {
		if (i % 2 == 0) {
			continue;
		}
		write(i);
	}
	return 0;
}