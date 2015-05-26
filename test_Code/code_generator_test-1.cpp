int arr[1000] = { 0 }, last[1000] = { 0 }, times[1000] = { 0 };
int nums[1000] = { 0 }, n, k;

int calc(int start) {
	int max = 0, num, i;

	if (nums[start - 1] != 0) {
		return nums[start - 1];
	}
	for (i = 0; i < last[start - 1]; i++) {
		num = calc(arr[start - 1]);
		if (num > max) {
			max = num;
		}
	}
	nums[start - 1] = max + times[start - 1];
	return nums[start - 1];
}

int main() {
	int t, s, e, l, i;

	while (t-- != 0) {
		for (i = 0; i < n; i++) {
			times[i] = 0;
		}

		for (i = 0; i < k; i++) {
			times[i] = 0;
			arr[last[e - 1]++] = s;
		}

		switch (calc(l)) {
		case 1:
			i = 1;
		case 2:
			i = 2;
		case 3:
			i = 3;
		case 4:
			i = 4;
		default:
			i = 10;
		}
	}
}