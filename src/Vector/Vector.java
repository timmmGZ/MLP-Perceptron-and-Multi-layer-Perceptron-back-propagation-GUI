package Vector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vector implements Comparable<Vector> {
	public double x, y, z, t;
	public String className;

	public Vector(double a, double b, double c, double d, String e) {
		x = a;
		y = b;
		z = c;
		t = d;
		className = e;
	}

	public static List<Vector> vectorParser(String file) throws FileNotFoundException {
		List<Vector> vList = new ArrayList<>();
		for (Scanner s = new Scanner(new FileInputStream(file)); s.hasNextLine();) {
			String[] line = s.nextLine().split(",");
			vList.add(new Vector(Double.parseDouble(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]),
					Double.parseDouble(line[3]), line[4]));
		}
		return vList;
	}

	public String toString() {
		return x + "," + y + "," + z + "," + t + "," + className;
	}

	@Override
	public int compareTo(Vector o) {
		double i;
		if ((i = x - o.x) != 0)
			if (i > 0)
				return 1;
			else
				return -1;
		if ((i = y - o.y) != 0)
			if (i > 0)
				return 1;
			else
				return -1;
		if ((i = z - o.z) != 0)
			if (i > 0)
				return 1;
			else
				return -1;
		if ((i = t - o.t) != 0)
			if (i > 0)
				return 1;
			else
				return -1;
		return className.compareTo(o.className);
	}

}