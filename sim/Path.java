
package pso;

import java.util.ArrayList;
import java.util.Random;

import static pso.Constant.scale;

public class Path {

	private ArrayList<Integer> order;
	private int ptsNum;
	
	public Path() {}
	
	public Path(int ptsNum) {
		order = new ArrayList<>();
		this.ptsNum = ptsNum;
		initRandomPath();
	}
	
	public Path(int ptsNum, ArrayList<Integer> order) {
		this.ptsNum = ptsNum;
		this.order = order;
	}

	private void initRandomPath() {
		ArrayList<Integer> temp = new ArrayList<>();
		for(int i = 0; i < ptsNum; i++) {
			temp.add(i);
		}
		
		Random random = new Random();
		for(int i = 0; i < ptsNum; i++) {
			int index = random.nextInt(temp.size());
			order.add(temp.remove(index));
		}
	}
	
	public Path variation() {
		Path result = new Path(ptsNum, (ArrayList<Integer>)order.clone());
		Random random = new Random();
		int index1 = random.nextInt(ptsNum);
		int index2;
		do {
			index2 = random.nextInt(ptsNum);
		} while(index1 == index2);
		
		Integer temp = result.getOrder().get(index1);
		
		result.getOrder().set(index1, order.get(index2));
		result.getOrder().set(index2, temp);
		return result;
	}
	
	public Path cross(Path path) {
		Path result = new Path(ptsNum, (ArrayList<Integer>)order.clone());
		Random random = new Random();
		int start = random.nextInt(ptsNum);
		int end;
		do {
			end = random.nextInt(ptsNum);
			
		} while(start == end);
		
		if(start > end) {
			int temp = start;
			start = end;
			end = temp;
		}
		
		for(int i = start; i <= end; i++) {
			result.getOrder().remove(path.getOrder().get(i));
		}
		
		for(int i = start; i <= end; i++) {
			result.getOrder().add(path.getOrder().get(i));
		}
		
		return result;
	}
	
	public ArrayList<Integer> getOrder() {
		return order;
	}
	
	public int getPtsNum() {
		return ptsNum;
	}

	public void setPtsNum(int ptsNum) {
		this.ptsNum = ptsNum;
	}

	public void setOrder(ArrayList<Integer> order) {
		this.order = order;
	}

	public double fitness() {
		int count = 1;
		for(int i = 1; i < order.size() - 1; i++) {
			int n = order.get(i);
			int nPrev = order.get(i-1);
			int nNext = order.get(i+1);
			int prev = Math.abs(n - nPrev);
			int next = Math.abs(n - nNext);
			
			if(prev == 1) {
				if(next == 1) {
					if((n % scale == 0) || ((n + 1)  % scale == 0)) {
						count += 2;
					}
				} else if (next == scale) {
					if((n % scale == 0) && (n > nPrev)) {
						count += 2;
					} else if((nPrev % scale == 0) && (n < nPrev)) {
						count += 2;
					} else {
						count += 1;
					}
				} else {
					count += 2;
				}
			} else if (prev == scale) {
				if(next == 1) {
					if((n % scale == 0) && (n > nNext)) {
						count += 2;
					} else if((nNext % scale == 0) && (n < nNext)) {
						count += 2;
					} else {
						count += 1;
					}
				} else if (next == scale) {
					// count += 0;
				} else {
					count += 2;
				}
			} else {
				if(next == 1 || next == scale) {
					count += 2;
				} else {
					count += 4;
				}
			}
		}
		return 1.0/count;
	}
	
}
