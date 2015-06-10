//if,else if , else
//for
//배열
//float-> int //자동 형변환

float cc;
int temp[40];
int i;

int B(int a,int b,char right){
		a = a*a;
		b = b*b;
		a++;
		b--;
		if(true)
			{right = '1';}
		else if( b == 1)
			{right = 'x';}
		else if (b == 2)
			{right = 's'; }
		else
			{right = 'n'; }

	}

int main()
{
	int x= 0 , y = 3;

	cc = 3.8;

	for(i=0; i<4;i++)
	{
		temp[i] = cc;
		cc += 2;
		
	}
	B(1,3, 'n');


}
