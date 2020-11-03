package pso;

import java.util.ArrayList;

/**
 * �û������Ⱥ�㷨��·�����������
 * 1, ��ʼ��n�����·����nΪ��Ⱥ��С
 * 2, ����ÿ��·������Ӧ��ֵ����ȡ��Ⱥ����ֵCp�Լ�������ʷ����ֵCg
 * 3, ��ÿ��·�����б���
 * 4, ������·��������Cp��Cg���н���
 * 5, ������Ӧ��ֵ������Ⱥ
 * 6, �����ظ�ֱ���ﵽ����������
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
			// ���콻��
			temp[i] = population[i].variation().cross(cp).cross(cg[i]);
			// ��Ⱥ����
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
			System.out.println("��"+i+"�ε���: " + cp.fitness());
		}
		
	}

	public Path getCp() {
		return cp;
	}

	public Path[] getPopulation() {
		return population;
	}
	
}
