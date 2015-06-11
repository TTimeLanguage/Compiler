bool checkTime(time check) {
	if (check < 12:0:0) {
		return true;
	} else {
		return false;
	}
}

int main() {
	int hour, min, sec, totalSec;
	time startTime;

	read(&hour);
	read(&min);
	read(&sec);

	read(&totalSec);

	startTime = makeTime(hour, min, sec);

	startTime += secToTime(totalSec);

	write(startTime);
	lf();

	if (checkTime(startTime)) {
		write('a');
		lf();
	} else {
		write('p');
		lf();
	}

	return 0;
}

bool checkTime(time src, time check) {
	if (src < check) {
		return true;
	} else {
		return false;
	}
}