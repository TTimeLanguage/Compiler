int main() {
	int i = 0, j, in;


	read(&in);

	for (; i < in; i++) {
		for (j = 0; j < in; j++) {
			write((float) i);
		}
		lf();
	}

	lf();
	for (i = 0; i < in; i++) {
		for (j = 0; j < in; j++) {
			if (i > 3 && j == 3) {
				continue;
			}
			write(j);
		}
		lf();
	}

}