package pso;

import java.util.ArrayList;

/**
 * 用混合粒子群算法对路径点进行排序
 * 1, 初始化n条随机路径，n为种群大小
 * 2, 计算每条路径的适应度值，获取种群最优值Cp以及个体历史最优值Cg
 * 3, 对每条路径进行变异
 * 4, 将变异路径依次与Cp和Cg进行交叉
 * 5, 根据适应度值更新种群
 * 6, 不断重复直到达到最大迭代次数
 */

public class HPSOTest {
	
	private int populationSize = 50;
	private int maxIteration = 2000000;
	private int ptsNum;
	private Path[] population;
	private Path cp;
	private Path[] cg;

	public HPSOTest(int ptsNum) {
		this.ptsNum = ptsNum;
		population = new Path[populationSize];
		cg = new Path[populationSize];
		init();
	}
	
	private void init() {
		double maxFitness = 0;
		int index = 0;
		for(int i = 0; i < populationSize; i++) {
			population[i] = new Path(ptsNum);
			cg[i] = new Path(ptsNum, (ArrayList<Integer>)(population[i].getOrder().clone()));
			if(population[i].fitness() > maxFitness) {
				maxFitness = population[i].fitness();
				index = i;
			}
		}
		cp = new Path(ptsNum, (ArrayList<Integer>)(population[index].getOrder().clone()));
	}
	
	public void variationAndCross() {
		Path[] temp = new Path[populationSize];
		
		for (int i = 0; i < population.length; i++) {
			// 变异交叉
			temp[i] = population[i].variation().cross(cp).cross(cg[i]);
			// 种群更新
			if(temp[i].fitness() > population[i].fitness()) {
				cg[i].setOrder(temp[i].getOrder());
			}
			population[i].setOrder(temp[i].getOrder());
			if(temp[i].fitness() > cp.fitness()) {
				cp.setOrder(temp[i].getOrder());
			}
		}
		
	}
	
	public void iterator() {
		System.out.println("iterator start");
		for(int i = 0; i < maxIteration; i++) {
			variationAndCross();
			System.out.println("第"+i+"次迭代: " + cp.fitness());
		}
		
	}

	public Path getCp() {
		return cp;
	}

	public Path[] getPopulation() {
		return population;
	}
	
}
