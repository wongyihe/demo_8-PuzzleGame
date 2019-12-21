import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class PuzzleGame {
static int formerway;	
static int currentposition;
static int targetposition;
static int tempposition;
static HashSet<String> history = new HashSet<String>(); //map history
static Stack<Integer> history_direction=new Stack<Integer>();//history direction
static boolean isUnique=true;
static int j=0;

static private boolean isPuzzleSolved(int []array,int []target_array)	
{
	int index=0;
	for(int i=0;i<9;i++)
		if(array[i]==target_array[i]) index++;
	if(index==target_array.length)return true;
	else return false;
	}
//return zero current position
static private int zeroposition(int []array,int []position_array) {
	int i;	
	for(i=0;i<9;i++) 
		if(array[i]==0)break;
	return position_array[i];
	
}
//return possible ways
//0 1 2
//3 4 5
//6 7 8
static private int []possibleways(){
	int []ways= {1,2,3,4};
	switch(tempposition) {
	case 0:ways[0]=0;ways[1]=0;break;
	case 1:ways[1]=0;break;
	case 2:ways[1]=0;ways[2]=0;break;
	case 3:ways[0]=0;break;
	case 4:break;
	case 5:ways[2]=0;break;
	case 6:ways[0]=0;ways[3]=0;break;
	case 7:ways[3]=0;break;
	case 8:ways[2]=0;ways[3]=0;break;
	}
	
	return ways;
	
}
//return possible ways avoid repeat
static private int[] waystogo(int[] ways) {
	
	switch(formerway) {
	case 0:break;
	case 1:ways[2]=0;break;
	case 2:ways[3]=0;break;
	case 3:ways[0]=0;break;
	case 4:ways[1]=0;break;
	}
	return ways;
	
}
//choose a way to go
//  2
//1 0 3
//  4
static private int chooseway(int []ways) {
	
	if(!isUnique) j++;//if duplicate change the priority
	int direction=0;
	int target=currentposition;
	for(int i=0;i<4;i++,j++) 
		if(ways[(j%4)]!=0&&ways[(j%4)]!=5) {direction=ways[((j)%4)];break;}
	target=DirectionToPosition(direction);
	formerway=direction;
	return target;
}
//input target direction to get target direction
static private int DirectionToPosition(int direction) {
	int position=0;
	switch(direction) {
	case 1:position=currentposition-1;break;
	case 2:position=currentposition-3;break;
	case 3:position=currentposition+1;break;
	case 4:position=currentposition+3;break;
	default:break;
	}	
	return position;
}

//move
static private void exchange(int[]array,int targetposition) {
	//交換內容物
	tempposition=currentposition;
	int tem=array[targetposition];
	array[targetposition]=array[tempposition];
	array[tempposition]=tem;
	//更新位置
	tempposition=targetposition;
}

static private void BacktoParent(int []array) {
	int direction=0;
	if(history_direction.empty()) {System.out.println("no solution");System.exit(0);}
	switch(history_direction.peek()) {
		case 1:direction=3;break;
		case 2:direction=4;break;
		case 3:direction=1;break;
		case 4:direction=2;break;
	}	
	
	//move&更新currentposition tempposition
	exchange(array,DirectionToPosition(direction));
	currentposition=DirectionToPosition(direction);
	history_direction.pop();
	System.out.println("come to mama");
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//set up
		int []position_array= {0,1,2,3,4,5,6,7,8};
		int []target_array= {1,2,3,4,5,6,7,8,0};
		int []array=new int[9];
		int []ways;
		formerway=0;
		int index=0;
		
		 //initiate map
		for(int i=0;i<array.length;i++)array[i]=target_array[i];
	    Random random = new Random();
	    for(int i=0; i < target_array.length/2; i ++){ 
	     int index_r = random.nextInt(9);
	     int tmp = array[index_r];
	     array[index_r] = array[i];
	     array[i] = tmp;
	   }
	   
	   //printout the random result
	   for(int i=0; i < target_array.length; i ++)
	     System.out.print("array["+(i+1)+"]=" + array[i]);
	   System.out.println("");

	   //searching
	   while(true) {
			if(isPuzzleSolved(array,target_array)) break;
			else 
			{
				System.out.println("--------------------");
				boolean isBackParent=false;
				int times=0;
				j=0;
				int []formerarray=new int[9];
				int []temarray=new int[9];
				
				//get blank space current position
				currentposition=zeroposition(array,position_array);
				
				do {
					//get target position
					for(int k=0;k<9;k++)temarray[k]=array[k];
					tempposition=currentposition;
					int []posibways=possibleways();
					ways=waystogo(posibways);
					targetposition=chooseway(ways);
					
					//move
					exchange(temarray, targetposition);
					// map
					String str="";
					for(int j=0;j<9;j++)
						str+=temarray[j];	
					isUnique=history.add(str);
					times++;
					if(times>3) {
						isBackParent=true;
						BacktoParent(array);
						break;
					}
				}while(!isUnique );
				index++;
				if(isBackParent) {}//do nothing
				else {
					//push formerway
					history_direction.push(formerway);
					System.out.println("formerway:"+formerway);
					
					//更新現在0的位置
					currentposition=tempposition;
					//更新現在的map
					for(int k=0;k<9;k++)
					{
						formerarray[k]=array[k];
						array[k]=temarray[k];
					}
					//show
					System.out.print(index+"     ");
					for(int k=0;k<9;k++)
						System.out.print(array[k]);
					System.out.println("");
				}
			}
	}
	
	System.out.println("done!!!!!!!!!!");

	}	
}





