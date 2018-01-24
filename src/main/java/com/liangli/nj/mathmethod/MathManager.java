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
			if (type.equalsIgnoreCase("ADD_SUB_2_3")) {
				mathQuestionBean = CalWholeTen.generator(gBean);
			}else if (type.equalsIgnoreCase("UNITCAL_1_1")||
					  type.equalsIgnoreCase("UNITCAL_5_2")) {
				mathQuestionBean = TransCal.generator(gBean);
				
			}else if (type.equalsIgnoreCase("MULTIPLY_5_1")) {
				mathQuestionBean = CalNumHas0.generator(gBean);
				
			}else if (type.equalsIgnoreCase("GEOFORMULA_5_1")||
					  type.equalsIgnoreCase("GEOFORMULA_5_2")||
					  type.equalsIgnoreCase("GEOFORMULA_6_1")||
					  type.equalsIgnoreCase("GEOFORMULA_9_1")||
					  type.equalsIgnoreCase("GEOFORMULA_9_2")||
					  type.equalsIgnoreCase("GEOFORMULA_9_3")) {
				mathQuestionBean = CalPerimeterAndArea.generator(gBean);
				
			}else if (type.equalsIgnoreCase("DIVIDE_6_1")||
					  type.equalsIgnoreCase("DIVIDE_6_2")) {
				mathQuestionBean = CalAnsHas0.generator(gBean);
				
			}else if (type.equalsIgnoreCase("DIVIDE_6_3")||
					  type.equalsIgnoreCase("ADD_1_7")||
					  type.equalsIgnoreCase("SUB_1_2")) {
				mathQuestionBean = IntegerBasicArithmetic.generator(gBean);
				
			}else if (type.equalsIgnoreCase("XIAOSHU_8_3")||
					  type.equalsIgnoreCase("ADD_4_12")||
					  type.equalsIgnoreCase("ADD_4_13")||
					  type.equalsIgnoreCase("ADD_5_2")||
					  type.equalsIgnoreCase("ADD_5_4")) {
				mathQuestionBean = DecimalBasicArithmetic.generator(gBean);
				
			}else if (type.equalsIgnoreCase("MULTIPLY_6_1")) {
				mathQuestionBean = CalWholeTen.generator(gBean);
				
			}else if (type.equalsIgnoreCase("TIMETRAN_6_1")) {
				mathQuestionBean = TimeTransform.generator(gBean);
				
			}else if (type.equalsIgnoreCase("TRANSFORM_7_1")||
					  type.equalsIgnoreCase("TRANSFORM_8_1")||
					  type.equalsIgnoreCase("TRANSFORM_8_2")) {
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
				
			}else if (type.equalsIgnoreCase("MULTIPLECHAR_10_1")||
					  type.equalsIgnoreCase("MULTIPLECHAR_10_2")||
					  type.equalsIgnoreCase("MULTIPLECHAR_10_3")) {
				mathQuestionBean = VolumeAndSurfaceArea.generator(gBean);
				
			}else if (type.equalsIgnoreCase("ADD_5_18")||
					  type.equalsIgnoreCase("ADD_5_19")) {
				mathQuestionBean = MaxComDivisorAndMinComMultiper.generator(gBean);
				
			}else if (type.equalsIgnoreCase("XIAOSHU_8_1")) {
				mathQuestionBean = DecimalDotMove.generator(gBean);
				
			}else if (type.equalsIgnoreCase("XIAOSHU_8_2")) {
				mathQuestionBean = DecimalApproximateValue.generator(gBean);
				
			}
			
			
			System.out.println("Type:" + type + "  " + "Name:" + name 
							 + "\n >>> " + mathQuestionBean.getQuestion() + mathQuestionBean.getAnswer());
		}
	}

}
