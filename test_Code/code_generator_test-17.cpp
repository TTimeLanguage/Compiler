// 새로운 타입 time,date 형 사용, 어느곳이나 변수선언 가능, 함수 사용 가능 및 오버로딩  
// 후위형 연산 가능, switch문 가능, 전역변수 가능,  
int k=3;
time t1 = 1:3:1;
date da = 2000.12.31;
int i = 5;
float j = 4;
int main()
{
    
	int t=0;
	switch(k){

	case 1 : t1=1:3:3;da=2000.12.29;break;
	case 3 : t1=1:3:2;da=2000.12.30;break;
	default : break;
	}


    
	t++;
	t=t+1;
	if(t==2){
	t=A(t);
	}

	return 0;
}

int A(int a){
	float k_2;
	k_2=i/a;
	a=A(a,k_2);
	return a;
}

int A(int a,float b){
	b*=j;
	return (int)b;
}

