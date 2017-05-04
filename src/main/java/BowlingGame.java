package main.java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 每一局保龄球比赛，也就是我们这里的一行， 都有 10 格。格被称之为“frame”。
在每一格里， 玩家有两次机会试图击倒全部 10 个瓶。
如果第一次就击倒了全部 10 个瓶，这称之为“strike”。这一格就结束了。
这一格的分数等于 10 加上接下来两球击倒的瓶数的总和。
如果一格中的第二个球击倒了全部 10 个瓶，称之为“spare”。这一格就结束了。
这一格的分数等于 10 加上接下来一个球击倒的瓶数。
如果两个球之后，仍然有瓶子没有被击倒，那么这一格的分数就是两次击倒的瓶数的总和。
如果你在最后一格（第 10 格）拿到一个 spare，你就会得到一次额外的发球机会。
如果你在最后一格拿到了 strike，你就会得到两次额外的机会。
如果额外的机会击倒了全部的球，不会重复之前的流程，额外机会的分数只用来计算最后一格的分数。
游戏的分数为所有格分数的总和。
 * X 表示一个 strike
/ 表示一个 spare
- 表示一个 miss
| 表示一格的分界线
|| 之后的字符表示最后一格的额外机会
 * 
 **/
public class BowlingGame{

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        int[][] scores = new int[11][4];
        BufferedReader buf = new BufferedReader (new InputStreamReader(System.in));
        String str = buf.readLine(); 
        /*解析输入的字符串并存入二维数组里----start**/
        String[] first=str.split("\\|\\|");
        String[] aa = first[0].split("\\|"); //String[] aa = "aaa|bbb|ccc".split("\\|"); 这样才能得到正确的结果
        scores=parseInputString(first,aa);
        /*解析输入的字符串并存入二维数组里----end**/
        int result=getBowlingScore(scores);
        System.out.println(result + "\t");
    }

    public static int getBowlingScore(int[][] scores){
        int res=0;
        for (int i = 0; i < scores.length-1; i++) {//前11轮
            if (scores[i][0] == 10 && i != 9) {
                if (scores[i + 1][0] == 10 && i < 8){ //前8轮，一次10分 就记后面2投球的分数
                    scores[i][2] = scores[i][0] + scores[i + 1][0]  + scores[i + 2][0];
                }else if (scores[i + 1][0] != 10 || i == 8){//第9轮10分 记第10轮2次的分数
                    scores[i][2] = scores[i][0]  + scores[i + 1][0]  + scores[i + 1][1];
                }
            }else{              
                if (scores[i][0] + scores[i][1] == 10 && i < 9){//前9轮 补中10分 加上下轮第一次的分数
                    scores[i][2] = scores[i][0] + scores[i][1] + scores[i + 1][0];
                }else{
                    scores[i][2] = scores[i][0] + scores[i][1];//2次不足10分 就记这么多
                }
            }
        }
        //求累计积分
        scores[0][3] = scores[0][2];
        for(int i = 1; i < scores.length;i++ ){
                scores[i][3]=scores[i][2] + scores[i - 1][3];
        }
        
        res=scores[scores.length-1][3];
        return res;
    }
    

    private static int[][] parseInputString(String[] first,String[] aa) {
        // TODO Auto-generated method stub
        int[][] scores = new int[11][4];
        for (int i = 0 ; i <aa.length ; i++ ) {                  
            if(aa[i].indexOf("X")!=-1){
                scores[i][0]=10;
                scores[i][1]=0;               
            }
            if(aa[i].indexOf("/")!=-1){
                scores[i][0]=Integer.parseInt(aa[i].split("/")[0]);
                scores[i][1]=10- scores[i][0];  
            }
               if(aa[i].indexOf("-")!=-1){             
                         if(aa[i].indexOf("-")==0){
                             scores[i][0]=0;                        
                             scores[i][1]= Integer.parseInt(aa[i].split("-")[1]);                        
                            }
                         else if(aa[i].indexOf("-")>=1){                     
                             scores[i][0]=Integer.parseInt(aa[i].split("-")[0]);   
                             scores[i][1]=0;   
                            }                
                }
                if(aa[i].equals("")){                       
                    i=i-1;
                    continue;
                }                 
        }
    //解析|| 之后的字符表示最后一格的额外机会
            if(first.length>=2){
                    if(first[1].length()==1){
                      //例如||后面为X
                       if(first[1].equals("X")==true){
                           scores[10][0] =10;
                           scores[10][2] =10;
                        }
                     //例如||后面为5
                       if(Integer.valueOf(first[1])<=10){
                           scores[10][0] =Integer.valueOf(first[1]);
                           scores[10][2] =Integer.valueOf(first[1]);
                       }                
                    }
                    else  if(first[1].length()==2){   
                     //例如||后面为XX
                     if(first[1].equals("XX")){ 
                         scores[9][1]=10;
                         scores[10][0]=10;
                         scores[10][2]=10;
                     }
                     if(first[1].equals("XX")!=true  && first[1].indexOf("X")!=-1){
                         //例如||后面X5
                         if(first[1].indexOf("X")==0){
                             scores[9][1]=10;
                             scores[10][0]=Integer.parseInt(first[1].substring(1,first[1].length()));
                             scores[10][2]=scores[10][0];
                         }
                         //例如||后面为5X
                         else if(first[1].indexOf("X")>=1){
                             scores[9][1]=Integer.parseInt(first[1].substring(0,first[1].indexOf("X")));
                             scores[10][0]=10;
                             scores[10][2]=scores[10][0];
                         }
                        
                        } 
                     //例如||后面为81
                      if(first[1].equals("XX")!=true  &&first[1].indexOf("X")==-1 && Integer.valueOf(first[1])>=10){
                        scores[9][1]=Integer.parseInt(first[1].substring(0,1));
                        scores[10][0]=Integer.parseInt(first[1].substring(1,2));
                        scores[10][2]=scores[10][0];
                       }                 
                    }
            }
            return scores;
    }
  }