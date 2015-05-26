int sum(int i) {
	int l = 0, sum = 0;

	for (l = 0; l < i; l++) {
		sum += l;
	}

	return sum;
}

bool test(int n1, int n2) {
	return n1 == n2;
}

time makeTime(int h, int m, int s) {
	return 1:2:1;
}

int main() {
	int i = 0, k = 0;
	
	if ( test(sum(10), sum(20)) ) {
		switch (makeTime(10, 54, 10)) {
		case 1:2:0:
			for (i = 0; i < 10; i++) {
				if (i == 9) {
					return i;
				}
			}
			break;
		case 2:1:0:
			while (test(sum(1), sum(2))) {
				k++;
			}
			break;
		}
	}

	return 20;
}
