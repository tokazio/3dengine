/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;

/**
 * http://www.cs.princeton.edu/~gewang/projects/darth/stuff/quat_faq.html#Q26
 *
 * @author rpetit
 */
public class Matrix3D {

    private double[][] Fmat;

    /**
     *
     */
    public Matrix3D(double[][] v) {
        Fmat = v;
    }

    /**
     *
     */
    public Matrix3D() {
        Fmat = new double[4][4];
    }

    /**
     *
     * @param aVector
     */
    public Matrix3D(Vector3D aVector) {
        Fmat = new double[][]{{aVector.X(), 0, 0, 0}, {aVector.Y(), 0, 0, 0}, {aVector.Z(), 0, 0, 0}, {0, 0, 0, 0}};
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public Matrix3D(double a, double b, double c, double d) {
        Fmat = new double[][]{{a, 0, 0, 0}, {b, 0, 0, 0}, {c, 0, 0, 0}, {d, 0, 0, 0}};
    }

    public final double get(int row, int col) {
        return Fmat[row][col];
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public final static Matrix3D multiply(Matrix3D a, Matrix3D b) {
        return multiply(new Matrix3D[]{a, b});
    }

    /**
     *
     * @param t
     * @return
     */
    public final static Matrix3D multiply(Matrix3D[] t) {
        Matrix3D r = t[0].clone();
        for (int i = 1; i < t.length; i++) {
            r = mul(r, t[i]);
        }
        return r;
    }

    //
    private static Matrix3D mul(Matrix3D a, Matrix3D b) {
        Matrix3D m = new Matrix3D();
        double q = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                q = 0;
                for (int d = 0; d < 4; d++) {
                    q += a.Fmat[r][d] * b.Fmat[d][c];
                }
                m.Fmat[r][c] = q;
            }
        }
        return m;
    }

    /**
     *
     * @return
     */
    @Override
    public final Matrix3D clone() {
        Matrix3D b = new Matrix3D();
        b.Fmat = Fmat.clone();
        return b;
    }

    /**
     *
     */
    public final void print() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(Fmat[i][j] + ",");
            }
            System.out.println("");
        }
    }

    /**
     *
     * @return
     */
    public static Matrix3D identity() {
        return new Matrix3D(new double[][]{
            {1.0, 0.0, 0.0, 0.0},
            {0.0, 1.0, 0.0, 0.0},
            {0.0, 0.0, 1.0, 0.0},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     *
     * @return
     */
    public static Matrix3D zero() {
        return new Matrix3D();
    }

    /**
     *
     * @return
     */
    public static Matrix3D one() {
        return new Matrix3D(new double[][]{
            {1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0}
        });
    }

    //TransformedVector = TranslationMatrix * RotationMatrix * ScaleMatrix * OriginalVector;
    /**
     *
     * @param aVector
     * @return
     */
    public static Matrix3D translation(Vector3D aVector) {
        return new Matrix3D(new double[][]{
            {1.0, 0.0, 0.0, aVector.X()},
            {0.0, 1.0, 0.0, aVector.Y()},
            {0.0, 0.0, 1.0, aVector.Z()},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     *
     * @param aVector
     * @return
     */
    public static Matrix3D scaling(Vector3D aVector) {
        return new Matrix3D(new double[][]{
            {aVector.X(), 0.0, 0.0, 0.0},
            {0.0, aVector.Y(), 0.0, 0.0},
            {0.0, 0.0, aVector.Z(), 0.0},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     * Rotation autour de x (Pitch) selon l'angle
     *
     * @param aVector
     * @return
     */
    public static Matrix3D rotationX(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        return new Matrix3D(new double[][]{
            {1.0, 0.0, 0.0, 0.0},
            {0.0, ca, -sa, 0.0},
            {0.0, sa, ca, 0.0},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     * Rotation autour de y (Yaw) selon l'angle
     *
     * @param aVector
     * @return
     */
    public static Matrix3D rotationY(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        return new Matrix3D(new double[][]{
            {ca, 0.0, sa, 0.0},
            {0.0, 1, 0, 0.0},
            {-sa, 0, ca, 0.0},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     * Rotation autour de z (Roll) selon l'angle
     *
     * @param aVector
     * @return
     */
    public static Matrix3D rotationZ(double angle) {
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        return new Matrix3D(new double[][]{
            {ca, -sa, 0.0, 0.0},
            {sa, ca, 0.0, 0.0},
            {0.0, 0.0, 1.0, 0.0},
            {0.0, 0.0, 0.0, 1.0}
        });
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static Matrix3D rotation(double x, double y, double z) {
        return rotation(new Vector3D(x, y, z));
    }

    /**
     *
     * @param aVector
     * @return
     */
    public static Matrix3D rotation(Vector3D aVector) {
        //ordre de multiplication des matrices de rotation X.Y.Z
        /* version classique
         Using basic matrix calculations, the operation count would reach
         128 multiplications, 96 additions and  80 assignments operations.
        
         Matrix3D rx=Matrix3D.rotationX(aVector.X());
         Matrix3D ry=Matrix3D.rotationX(aVector.Y());
         Matrix3D rz=Matrix3D.rotationX(aVector.Z());
         return Matrix3D.multiply(new Matrix3D[]{rx,ry,rz});
         */
        /*version optimisée
         Using the optimised algorithm, only 12 multiplications, 6 subtractions
         and 18 assignment operations are required.
         */
        double A = Math.cos(aVector.X());
        double B = Math.sin(aVector.X());
        double C = Math.cos(aVector.Y());
        double D = Math.sin(aVector.Y());
        double E = Math.cos(aVector.Z());
        double F = Math.sin(aVector.Z());

        double AD = A * D;
        double BD = B * D;

        return new Matrix3D(new double[][]{
            {C * E, -C * F, D, 0},
            {BD * E + A * F, -BD * F + A * E, -B * C, 0},
            {-AD * E + B * F, AD * F + B * E, A * C, 0},
            {0, 0, 0, 1}
        });

    }

    /**
     * http://msdn.microsoft.com/en-us/library/windows/desktop/bb281710(v=vs.85).aspx
     *
     * @param eye Position de la caméra
     * @param target Position où vise la caméra
     * @param head Vertical vers le haut (0,1,0)
     * @return
     */
    public static Matrix3D lookAtLH(Vector3D eye, Vector3D target, Vector3D head) {
        Vector3D zaxis = Vector3D.sub(target, eye).normal();    // The "forward" vector.
        Vector3D xaxis = Vector3D.cross(head, zaxis).normal();// The "right" vector.
        Vector3D yaxis = Vector3D.cross(zaxis, xaxis).normal();     // The "up" vector.
        return new Matrix3D(new double[][]{
            {xaxis.X(), yaxis.X(), zaxis.X(), 0},
            {xaxis.Y(), yaxis.Y(), zaxis.Y(), 0},
            {xaxis.Z(), yaxis.Z(), zaxis.Z(), 0},
            {-Vector3D.dot(xaxis, eye), -Vector3D.dot(yaxis, eye), -Vector3D.dot(zaxis, eye), 1}
        });
    }

    /**
     * http://msdn.microsoft.com/en-us/library/windows/desktop/bb281727(v=vs.85).aspx
     *
     * @param FoV FieldOfView en dégré
     * @param ratio Aspect ratio (1 pour une vue carrée ou w/h)
     * @param ncp Near clipping plane distance (z devant)
     * @param fcp Far clipping plane distance (z fond)
     * @return
     */
    public static Matrix3D perspectiveFovLH(double FoV, double ratio, double ncp, double fcp) {
        double h = Math.tan(Math.PI / 2 - degToRad(FoV) / 2);//2 * ncp / (right - left);
        double w = h / ratio;
        double a = fcp / (fcp - ncp);
        double b = -ncp * fcp / (fcp - ncp);
        double c = 0;
        double d = 0;
        return new Matrix3D(new double[][]{
            {w, 0, 0, 0},
            {0, h, 0, 0},
            {c, d, a, 1},
            {0, 0, b, 0}
        });
    }

    public static double degToRad(double deg) {
        return (Math.PI * deg) / 180;
    }
}
