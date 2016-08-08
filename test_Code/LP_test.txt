int main()
{
	int x,y,z,a;
	char c, h;

	x = 10;
	y = 7;
	z = -8;
	a = 0;
	c = 'A';
	h = 'Z';

	if (x>y)
	{
		a = 1;
	}
	else if (x>z)
	{
		for (z; z <= x; z++)
		{
			a++;
		}
	}
	else
	{
		a = 2;
	}


	switch (a > 10)
	{
	case a : return 2; break;
	case 2 :
		return 1; break;
	case 2.3 : break;
	case 1.2.3 : break;
	case 1:2:3 : break;
	}

	while (a--)
	{
		return 100;
	}

}