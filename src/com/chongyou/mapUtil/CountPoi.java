package com.chongyou.mapUtil;

import java.util.List;


public class CountPoi {
	private double[] poi=new double[2];
	private double[] sum=new double[364];//363个参考点的每个的差值
	private double[] sum2=new double[364];
	private double m;//一个参考点差值  每个差值是5个ap的总和
	
	
	public double[] count(List<WifiAPInfo> wifiAPInfoList2,WifiAPInfo wifiAPInfo_unknow){
//		第一步 检查 wifiAPInfoList2 wifiAPInfo_unknow就是数据库和待测点wifi
//		System.out.println("wifiAPInfoList2:"+wifiAPInfoList2.toString());
		System.out.println("wifiAPInfo_unknow:"+wifiAPInfo_unknow);
		
		sum[0]=0;
		sum2[0]=0;
		//判断wifiAPInfo_unknow测到的5个ap都不是0 os
		if(wifiAPInfo_unknow.getAP1()!=0
				&&wifiAPInfo_unknow.getAP2()!=0
				&&wifiAPInfo_unknow.getAP3()!=0
				&&wifiAPInfo_unknow.getAP4()!=0
				&&wifiAPInfo_unknow.getAP5()!=0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=Math.pow((info.getAP1()-wifiAPInfo_unknow.getAP1()),2)+
						+Math.pow((info.getAP2()-wifiAPInfo_unknow.getAP2()),2)
						+Math.pow((info.getAP3()-wifiAPInfo_unknow.getAP3()),2)
						+Math.pow((info.getAP4()-wifiAPInfo_unknow.getAP4()),2)
						+Math.pow((info.getAP5()-wifiAPInfo_unknow.getAP5()),2);
	//			第二步：检查363个参考点和待测电的信号平方差值
	//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}else{
			oneApIs0(wifiAPInfoList2, wifiAPInfo_unknow);
		}
		
		for(int i=0;i<sum.length;i++)
			sum2[i]=sum[i];
		paixu(sum);
		double xSum=0;
		double ySum=0;
		//加权
		double w=0;
		for(int i=1;i<=4;i++){
			w=1.0/sum[i]+w;
			System.out.println("1/sum[i]:"+1/sum[i]);
		}
		System.out.println("权重的和是"+w);
		for(int i=1;i<=4;i++){
			for(int j=0;j<sum.length;j++){
				if(sum[i]==sum2[j]){
					xSum=xSum+1.0/sum[i]/w*(wifiAPInfoList2.get(j).getX());
					ySum=ySum+1.0/sum[i]/w*(wifiAPInfoList2.get(j).getY());
//					第四步 检查四个坐标 可以查看数据库  看j对应数据库的四个参考点信号十分和wifi采集器里待测点信号相似
					System.out.println("最邻近的4个坐标"+i+"_______________:"+j);
				}
			}
		}
		System.out.println("xSum:"+xSum);
		poi[0]=xSum;
		poi[1]=ySum;
		
		
//		第三步：检查sum2 再检查sum分别是未排序和排序后和参考点差值的集合
//		for(double n:sum2){
//			System.out.println("开始了：+++++++++"+n+",");
//		}
		return poi;
	}

	
	private double[] paixu(double[] array) {
		for(int x=0;x<array.length-1;x++){
			for(int y=x+1;y<array.length;y++){
				if(array[x]>array[y]){
					double temp=array[x];//  临时变量记录最大值
					array[x]=array[y];
					array[y]=temp;
				}
			}
		}
		return array;
	}

	private void oneApIs0(List<WifiAPInfo> wifiAPInfoList2,
			WifiAPInfo wifiAPInfo_unknow) {
		if(wifiAPInfo_unknow.getAP1()==0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=+Math.pow((info.getAP2()-wifiAPInfo_unknow.getAP2()),2)
						+Math.pow((info.getAP3()-wifiAPInfo_unknow.getAP3()),2)
						+Math.pow((info.getAP4()-wifiAPInfo_unknow.getAP4()),2)
						+Math.pow((info.getAP5()-wifiAPInfo_unknow.getAP5()),2);
//			第二步：检查363个参考点和待测电的信号平方差值
//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}
		else if(wifiAPInfo_unknow.getAP2()==0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=Math.pow((info.getAP1()-wifiAPInfo_unknow.getAP1()),2)+
						+Math.pow((info.getAP3()-wifiAPInfo_unknow.getAP3()),2)
						+Math.pow((info.getAP4()-wifiAPInfo_unknow.getAP4()),2)
						+Math.pow((info.getAP5()-wifiAPInfo_unknow.getAP5()),2);
//			第二步：检查363个参考点和待测电的信号平方差值
//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}
		else if(wifiAPInfo_unknow.getAP3()==0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=Math.pow((info.getAP1()-wifiAPInfo_unknow.getAP1()),2)+
						+Math.pow((info.getAP2()-wifiAPInfo_unknow.getAP2()),2)
						+Math.pow((info.getAP4()-wifiAPInfo_unknow.getAP4()),2)
						+Math.pow((info.getAP5()-wifiAPInfo_unknow.getAP5()),2);
//			第二步：检查363个参考点和待测电的信号平方差值
//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}
		else if(wifiAPInfo_unknow.getAP4()==0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=Math.pow((info.getAP1()-wifiAPInfo_unknow.getAP1()),2)+
						+Math.pow((info.getAP2()-wifiAPInfo_unknow.getAP2()),2)
						+Math.pow((info.getAP3()-wifiAPInfo_unknow.getAP3()),2)
						+Math.pow((info.getAP5()-wifiAPInfo_unknow.getAP5()),2);
//			第二步：检查363个参考点和待测电的信号平方差值
//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}
		else if(wifiAPInfo_unknow.getAP5()==0){
			for(int i=0;i<wifiAPInfoList2.size();i++){
				WifiAPInfo info=wifiAPInfoList2.get(i);
				m=Math.pow((info.getAP1()-wifiAPInfo_unknow.getAP1()),2)+
						+Math.pow((info.getAP2()-wifiAPInfo_unknow.getAP2()),2)
						+Math.pow((info.getAP3()-wifiAPInfo_unknow.getAP3()),2)
						+Math.pow((info.getAP4()-wifiAPInfo_unknow.getAP4()),2);
//			第二步：检查363个参考点和待测电的信号平方差值
//			System.out.println("中间363个参考点和待测电的信号平方差值："+m);
				sum[i+1]=Math.sqrt(m);
			}
		}
	}

}
