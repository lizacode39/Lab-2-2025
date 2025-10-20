package functions;

public class TabulatedFunction {
    private FunctionPoint[] pointValue;
    private int sizeValue;

    public TabulatedFunction(double leftX, double rightX, int pointCount) {
        sizeValue = pointCount;
        pointValue = new FunctionPoint[2 * sizeValue];
        double step = (rightX - leftX) / (sizeValue - 1);
        for(int i = 0; i < sizeValue; i++){
            pointValue[i] = new FunctionPoint(leftX + step * i, 0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] value)
    {
        sizeValue = value.length;
        pointValue = new FunctionPoint[2 * sizeValue];
        double step = (rightX - leftX) / (sizeValue - 1);
        for(int i = 0; i < sizeValue; i++){
            pointValue[i] = new FunctionPoint(leftX + step * i, value[i]);
        }
    }

    public double getLeftDomainBorder(){
        return pointValue[0].getX();
    }

    public double getRightDomainBorder(){
        return pointValue[sizeValue - 1].getX();
    }

    public double getFunctionValue(double x){
        int index;
        if ((pointValue[0].getX() <= x) && (x <= pointValue[sizeValue-1].getX())) {
            for (index = 0; index < sizeValue && pointValue[index].getX() < x; index++);
            double x_1 = pointValue[index - 1].getX();
            double y_1 = pointValue[index - 1].getY();
            double x_2 = pointValue[index].getX();
            double y_2 = pointValue[index].getY();
            return ((x - x_1) * (y_2 - y_1)) / (x_2 - x_1) + y_1;
        }
        return Double.NaN;
    }

    public int getPointCount(){
        return sizeValue;
    }

    public FunctionPoint getPoint(int index){
        return new FunctionPoint(pointValue[index]);
    }

    public void setPoint(int index, FunctionPoint point){
        if ((index != 0) && (index != sizeValue-1)){
            if((pointValue[index-1].getX() < point.getX()) && (point.getX() < pointValue[index+1].getX())){
                pointValue[index] = new FunctionPoint(point);
            }
        }
        else if ((index == 0) && (point.getX() < pointValue[1].getX())) {
                pointValue[index] = new FunctionPoint(point);
            }
        else if ((index == sizeValue-1) && (pointValue[index-1].getX() < point.getX())) {
                pointValue[index] = new FunctionPoint(point);
            }
        else return;
    }

    public double getPointX(int index){
        return pointValue[index].getX();
    }

    public double getPointY(int index){
        return pointValue[index].getY();
    }

    public void setPointX(int index, double x){
        setPoint(index, new FunctionPoint(x, pointValue[index].getY()));
    }

    public void setPointY(int index, double y){
        pointValue[index].setY(y);
    }

    public void deletePoint(int index){
        pointValue[index] = null;
        for (int i = index; i < sizeValue-1; i++){
            pointValue[i] = pointValue[i+1];
        }
        --sizeValue;
    }

    public void addPoint(FunctionPoint point){
        if(pointValue.length < sizeValue + 1){
            FunctionPoint[] newPointValue = new FunctionPoint[sizeValue + (sizeValue / 2)];
            System.arraycopy(pointValue, 0, newPointValue, 0, pointValue.length);
            pointValue = null;
            pointValue = newPointValue;
            newPointValue = null;
        }
        int index;

        for (index = 0; index < sizeValue && pointValue[index].getX() < point.getX(); index++);

        if(index != sizeValue && pointValue[index].getX() == point.getX()){
            System.out.println("This 'x' value already exists, set is used");
            setPoint(index, point);
        }
        else {
            System.arraycopy(pointValue, index, pointValue, index+1, sizeValue-index);
            pointValue[index] = new FunctionPoint(point);
            ++sizeValue;
        }
    }


    public void printTabFun(){
        for(int i = 0; i < sizeValue; i++){
            System.out.println("№" + (i+1) + " x:" + pointValue[i].getX() + " y:" + pointValue[i].getY());
        }
    }
}
