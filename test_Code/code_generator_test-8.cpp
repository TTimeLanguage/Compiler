int main() {
	int n = -20150560;
	int day, month, year, tmp;
	int arr[12] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	read(&n);

	year = n / 10000;
	day = n % 100;
	month = n % 10000 / 100;

	year += month / 12;
	month %= 12;

	if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
		arr[1] = 29;
	}

	tmp = arr[month - 1];
	month += day / tmp;
	year += month / 12;
	month %= 12;
	day %= tmp;

	n = year * 10000 + month * 100 + day;

	write(n);

	return n;
}