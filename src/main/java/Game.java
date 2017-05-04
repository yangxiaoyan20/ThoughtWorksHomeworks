package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws IOException {
          Scanner sc = new Scanner(System.in);
          int[][] scores = new int[11][4];

          for (int i = 0; i < scores.length; i++) {//循环11轮
              if (i < scores.length - 1) {//循环前10轮
                  System.out.printf("请输入第%d轮，第1次打倒的个数：", i + 1);
                  scores[i][0] = sc.nextInt();
                  if (scores[i][0] < 10 || i == 9) {//前9轮 不是10 就再记第二次分数 ；第10轮无论如何都记2次
                      System.out.printf("请输入第%d轮，第2次打倒的个数：", i + 1);
                      scores[i][1] = sc.nextInt();
                  }
              } else if (scores[i - 1][0] + scores[i - 1][1] >= 10) {//第10轮10分以上 11轮记一次分数
                  System.out.printf("请输入第%d轮,第%d次打倒的个数：", i, 3);
                  scores[i][0] = scores[i][2] = sc.nextInt();
              }
          }

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
          //打印所有
          for(int i = 0 ; i < scores.length; i++){
              for(int j = 0 ; j < scores[i].length; j++){
                  System.out.print(scores[i][j] + "\t");
              }
              System.out.println("\t第" + (i + 1) +"轮\n");
          }
      }
}
