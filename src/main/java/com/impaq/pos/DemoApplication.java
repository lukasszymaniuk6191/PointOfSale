package com.impaq.pos;

import com.impaq.pos.main.PointOfSale;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoApplication {




	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(DemoApplication.class);
		PointOfSale printer = ctx.getBean(PointOfSale.class);
		printer.shopping();
		ctx.close();


	}
}
