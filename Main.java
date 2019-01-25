import java.util.ArrayList;
import java.util.Scanner;

class Main
{
	public static void main(String[] args)
	{
		// this code reads the input data into a couple of lists
		Scanner reader = new Scanner(System.in);
		int w = reader.nextInt();
		int h = reader.nextInt();
		int[][] tree = new int[h][w];
		for(int j = 0; j < h; ++j)
		{
			for(int i = 0; i < w; ++i)
			{
				tree[j][i] = reader.nextInt();
			}
		}
		reader.close();

		// this will now be nested list
		// to get the value at coordinate (i, j) you would write tree[j][i]
		// i.e. row then column.
		// note that these are 0-indexed, not 1-indexed like the description.

		// TODO: implement your algorithm
		int answer = 0;
		answer=maximum_happy(tree,h,w);
		// output a single number representing the solution
		// (your program should not output any other text)
		System.out.println(answer);
	}
	
	private static int maximum_happy(int[][] tree,int h,int w){
		
		int[][] OPT=new int[h][w];
		//base case
		OPT[h-1][w-1]=0;//starting point
		int impossible_position=Integer.MIN_VALUE;
		
		
		int c_OPT=0;
		int j_OPT=0;
		int h_OPT=0;
		int conse=0;
		int start_i=w-1;
		int start_j=h-1;
		
		for(int j_index=h-1;j_index>=0;j_index--){
			for(int i_index=w-1;i_index>=0;i_index--){
				if(i_index==start_i&&j_index==start_j){
					continue;
				}
				else if(j_index==start_j){ //at the top of tree
					conse++;
					h_OPT=hop(tree,OPT,j_index,i_index,conse);
					c_OPT=Math.max(h_OPT,impossible_position);
					OPT[j_index][i_index]=c_OPT;
				}
				else if(i_index==start_i){ //at the most left
					j_OPT=jump(tree,OPT,j_index,i_index,h);
					c_OPT=Math.max(j_OPT,impossible_position);
					OPT[j_index][i_index]=c_OPT;
					//conse=0;
				}
				else{
					//conse++;
					h_OPT=hop(tree,OPT,j_index,i_index,0,w,h);
					j_OPT=jump(tree,OPT,j_index,i_index,h);
					if(h_OPT>j_OPT){
						OPT[j_index][i_index]=h_OPT;
					}
					else if(h_OPT<j_OPT){
						OPT[j_index][i_index]=j_OPT;
						//conse=0;
					}
				}
			}
		}
		return OPT[0][0];
	}
	
	private static int jump(int[][] tree,int[][] OPT,int h,int w,int tree_height){
		int c_opt=Integer.MIN_VALUE;
		int p_opt=Integer.MIN_VALUE;
		
		for(int i=h+1;i<tree_height;i++){
			c_opt=tree[h][w]+OPT[i][w];
			if(c_opt>p_opt){
				p_opt=c_opt;
			}
		}
		return p_opt;
	}
	
	private static int hop(int[][] tree,int[][] OPT,int h,int w,int conse){
		int c_opt;
		c_opt=OPT[h][w+1]+tree[h][w]-conse;
		return c_opt;
	}
	
	private static int hop(int[][] tree,int[][] OPT,int h,int w,int conse,int tree_width,int tree_height){
		int c_opt=Integer.MIN_VALUE;
		int p_opt=Integer.MIN_VALUE;
		int max=Integer.MIN_VALUE;
		int result=0;
		int result_w=tree[h][w];
		int result_j=0;
		
		for(int i=w+1;i<tree_width;i++){
			p_opt=Integer.MIN_VALUE;
			for(int j=h+1;j<tree_height;j++){
				c_opt=OPT[j][i];
				if(p_opt<c_opt){
					p_opt=c_opt;
				}
			}
			result_j=p_opt+tree[h][i];
			conse=conse+(i-w);
			result=result_w+result_j-conse;
			result_w=result_w+tree[h][i];
			if(max<result){
			max=result;
			}
		}
		return max;
	}
	
}
