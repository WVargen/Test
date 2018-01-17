package com.liangli.nj.mathmethod;

import java.lang.reflect.Method;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.bean.QuestionBean;
import com.liangli.nj.bean.t_math_question_tmp;
import com.liangli.nj.database.DatabaseAccessor;
import com.liangli.nj.utils.Definition;
import com.liangli.nj.utils.DeviceUtils.encode;

public class MathManager {
	public static void mathManager() throws Exception {
		Definition.printSqlInConsol = true;   	 	
		List<t_math_question_tmp> dataList = DatabaseAccessor.get().getSelect()
		 	.select(t_math_question_tmp.class)
		 	.find(t_math_question_tmp.class);
		for (t_math_question_tmp t_math_question_tmp : dataList) {
			
			String type = t_math_question_tmp.getType();
			String pattern = t_math_question_tmp.getPattern();
			GeneratorBean gBean = t_math_question_tmp.getGeneratorBean();
			String name = t_math_question_tmp.getName();
			
//			Class<?> generatorCls = Class.forName("com.liangli.nj.mathmethod." + generatorBean.getGenerator());
//			Object generatorObj = generatorCls.newInstance();
//			Method generator = generatorCls.getDeclaredMethod("generator", GeneratorBean.class);	
//			
//			MathQuestionBean mathQuestionBean = (MathQuestionBean) generator.invoke(generatorObj, generatorBean);
//			System.out.println(mathQuestionBean.getQuestion() + mathQuestionBean.getAnswer());
//			
			
			MathQuestionBean mathQuestionBean = null;
			if (type.equalsIgnoreCase("ADD_1_7")||
				type.equalsIgnoreCase("ADD_1_8")||
				type.equalsIgnoreCase("ADD_2_1")) {
				mathQuestionBean = CalWholeTen.generator(gBean);
			}else if (type.equalsIgnoreCase("ADD_1_9")||
					  type.equalsIgnoreCase("ADD_3_1")) {
				mathQuestionBean = TransCal.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_3_2")) {
				mathQuestionBean = CalNumHas0.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_3_3")||
					  type.equalsIgnoreCase("ADD_3_4")||
					  type.equalsIgnoreCase("ADD_3_9")||
					  type.equalsIgnoreCase("ADD_5_8")||
					  type.equalsIgnoreCase("ADD_5_9")||
					  type.equalsIgnoreCase("ADD_5_10")) {
				mathQuestionBean = CalPerimeterAndArea.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_3_5")||
					  type.equalsIgnoreCase("ADD_3_6")) {
				mathQuestionBean = CalAnsHas0.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_3_7")) {
				mathQuestionBean = IntegerBasicArithmetic.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_6")||
					  type.equalsIgnoreCase("ADD_4_12")||
					  type.equalsIgnoreCase("ADD_4_13")||
					  type.equalsIgnoreCase("ADD_5_2")||
					  type.equalsIgnoreCase("ADD_5_4")) {
				mathQuestionBean = DecimalBasicArithmetic.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_3_8")) {
				mathQuestionBean = CalWholeTen.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_1")) {
				mathQuestionBean = TimeTransform.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_2_1")||
					  type.equalsIgnoreCase("ADD_5_16")||
					  type.equalsIgnoreCase("ADD_5_17")) {
				mathQuestionBean = TransUnit.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_3")||
					  type.equalsIgnoreCase("ADD_4_4")||
					  type.equalsIgnoreCase("ADD_4_7")||
					  type.equalsIgnoreCase("ADD_5_5")) {
				mathQuestionBean = FourArithmeticOperation.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_5")) {
				mathQuestionBean = CompareNum.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_8")||
					  type.equalsIgnoreCase("ADD_4_9")||
					  type.equalsIgnoreCase("ADD_4_2_2")) {
				mathQuestionBean = NumberReadandWrite.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_5_11")||
					  type.equalsIgnoreCase("ADD_5_12")) {
				mathQuestionBean = MultipleCharacteristics.generator(gBean);		
				
			}else if (type.equalsIgnoreCase("ADD_5_13")||
					  type.equalsIgnoreCase("ADD_5_14")||
					  type.equalsIgnoreCase("ADD_5_15")) {
				mathQuestionBean = VolumeAndSurfaceArea.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_5_18")||
					  type.equalsIgnoreCase("ADD_5_19")) {
				mathQuestionBean = MaxComDivisorAndMinComMultiper.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_10")) {
				mathQuestionBean = DecimalDotMove.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_4_11")) {
				mathQuestionBean = DecimalApproximateValue.generator(gBean);
				
			}
			
			
			System.out.println("Type:" + type + "  " + "Name:" + name 
							 + "\n >>> " + mathQuestionBean.getQuestion() + mathQuestionBean.getAnswer());
		}
	}

}
