/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentBehaviorEvaluator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.service
 *
 * @Description : Evaluates bounded synthetic compressor and valve characteristic maps.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import java.util.List;

/** Explicit V1 synthetic maps; no extrapolation, operational calibration or compressor heat model. */
public final class SimulationEquipmentBehaviorEvaluator {
    public static final String HEAD = "SYNTHETIC_ISOTHERMAL_HEAD_V1";
    public static final String EFFICIENCY = "SYNTHETIC_ISOTHERMAL_EFFICIENCY_V1";
    public static final String LINEAR = "SYNTHETIC_RECTANGULAR_LINEAR_V1";
    public static final String VALVE = "SYNTHETIC_FORWARD_DP_TABLE_V1";
    public static final double R = 8.31446261815324;
    public record CompressorValue(double headJoulesPerKilogram, double efficiency,
            double headFlowDerivative, double headSpeedDerivative,
            double efficiencyFlowDerivative, double efficiencySpeedDerivative) { }
    public record ValveValue(double massFlowKilogramsPerSecond, double flowDifferentialPressureDerivative,
            double flowOpeningDerivative, boolean closed) { }
    private record Cell(int left, double fraction, double span) { }
    private record Interpolated(double value, double innerDerivative, double outerDerivative) { }

    public CompressorValue compressor(CompressorCurve curve, double q, double rpm) {
        if (curve == null || curve.origin() != Origin.SYNTHETIC || !HEAD.equals(curve.headDefinitionReference())
                || !EFFICIENCY.equals(curve.efficiencyDefinitionReference())
                || !LINEAR.equals(curve.interpolationMethodReference())) {
            throw new IllegalArgumentException("Unknown or non-synthetic compressor method.");
        }
        List<SpeedLine> lines = curve.speedLines();
        double[] outer = new double[lines.size()];
        for (int i=0;i<outer.length;i++) outer[i] = lines.get(i).rotationalSpeedRevolutionsPerMinute().doubleValue();
        var h = interpolate(outer, lines.stream().map(l -> l.points().stream()
                .map(pt -> pt.massFlowKilogramsPerSecond().doubleValue()).toArray(Double[]::new))
                .map(a -> unbox(a)).toList(),
                lines.stream().map(l -> l.points().stream().mapToDouble(pt -> pt.specificHeadJoulesPerKilogram().doubleValue()).toArray()).toList(),q,rpm);
        var e = interpolate(outer, lines.stream().map(l -> l.points().stream()
                .mapToDouble(pt -> pt.massFlowKilogramsPerSecond().doubleValue()).toArray()).toList(),
                lines.stream().map(l -> l.points().stream().mapToDouble(pt -> pt.efficiencyFraction().doubleValue()).toArray()).toList(),q,rpm);
        if (!(h.value() >= 0) || !(e.value() > 0 && e.value() <= 1)) throw new IllegalArgumentException("Invalid compressor interpolation.");
        return new CompressorValue(h.value(),e.value(),h.innerDerivative(),h.outerDerivative(),e.innerDerivative(),e.outerDerivative());
    }
    private static double[] unbox(Double[] source) {
        double[] out=new double[source.length];for(int i=0;i<out.length;i++)out[i]=source[i];return out;
    }
    public ValveValue valve(ValveCharacteristic curve, String method, double dp, double opening) {
        if (curve == null || curve.origin()!=Origin.SYNTHETIC || !VALVE.equals(method))
            throw new IllegalArgumentException("Unknown or non-synthetic valve method.");
        var lines=curve.openingLines();
        double[] outer=lines.stream().mapToDouble(l->l.openingFraction().doubleValue()).toArray();
        List<double[]> x=lines.stream().map(l->l.points().stream().mapToDouble(pt->pt.differentialPressurePascals().doubleValue()).toArray()).toList();
        List<double[]> y=lines.stream().map(l->l.points().stream().mapToDouble(pt->pt.massFlowKilogramsPerSecond().doubleValue()).toArray()).toList();
        for (int i=0;i<x.size();i++) {
            if (x.get(i)[0]!=0 || y.get(i)[0]!=0) throw new IllegalArgumentException("Valve map must start at zero dp/flow.");
            for(int j=1;j<x.get(i).length;j++) {
                if (x.get(i)[j]<=x.get(i)[j-1] || y.get(i)[j]<y.get(i)[j-1])
                    throw new IllegalArgumentException("Nonmonotonic valve line.");
                if (outer[i]>0 && !(y.get(i)[j]>y.get(i)[j-1]))
                    throw new IllegalArgumentException("Flat active valve line.");
            }
        }
        var out=interpolate(outer,x,y,dp,opening);
        boolean closed=opening==0 && out.value()==0;
        if (!closed && !(out.innerDerivative()>0)) throw new IllegalArgumentException("Unsupported zero-flow active valve map.");
        return new ValveValue(out.value(),out.innerDerivative(),out.outerDerivative(),closed);
    }
    /** Deliberately exact bracket choice: right cell at inner knot, left at maximum. */
    private static Interpolated interpolate(double[] outer, List<double[]> grids, List<double[]> values,
            double innerValue, double outerValue) {
        if (outer.length==0 || grids.size()!=outer.length || values.size()!=outer.length)
            throw new IllegalArgumentException("Incomplete rectangular map.");
        for(double o:outer) if(!Double.isFinite(o)) throw new IllegalArgumentException("Nonfinite map coordinate.");
        for(int k=1;k<outer.length;k++) if (!(outer[k]>outer[k-1])) throw new IllegalArgumentException("Unordered control lines.");
        double[] grid=grids.get(0);
        if(grid.length<2)throw new IllegalArgumentException("Map must have two points.");
        for(int i=0;i<grids.size();i++) {
            if(grids.get(i).length!=grid.length || values.get(i).length!=grid.length)
                throw new IllegalArgumentException("Nonrectangular map.");
            for(int j=0;j<grid.length;j++) {
                if(!Double.isFinite(grids.get(i)[j])||!Double.isFinite(values.get(i)[j])||grids.get(i)[j]!=grid[j])
                    throw new IllegalArgumentException("Nonrectangular or nonfinite map.");
                if(j>0&&!(grid[j]>grid[j-1]))throw new IllegalArgumentException("Nonincreasing map coordinate.");
            }
        }
        Cell inner=cell(grid,innerValue);
        Cell control=outer.length==1 ? new Cell(0,0,1) : cell(outer,outerValue);
        double[] v=new double[2],dx=new double[2];
        for(int k=0;k<(outer.length==1?1:2);k++) {
            int i=control.left()+k, j=inner.left();
            v[k]=values.get(i)[j]*(1-inner.fraction())+values.get(i)[j+1]*inner.fraction();
            dx[k]=(values.get(i)[j+1]-values.get(i)[j])/inner.span();
        }
        if(outer.length==1)return new Interpolated(v[0],dx[0],0);
        double u=control.fraction();
        return new Interpolated(v[0]*(1-u)+v[1]*u,dx[0]*(1-u)+dx[1]*u,(v[1]-v[0])/control.span());
    }
    private static Cell cell(double[] grid,double value) {
        if(!Double.isFinite(value)||value<grid[0]||value>grid[grid.length-1])
            throw new IllegalArgumentException("Map extrapolation is not allowed.");
        int j=grid.length-2;
        for(int k=0;k<grid.length-1;k++)if(value<grid[k+1]) {j=k;break;}
        double width=grid[j+1]-grid[j];
        if(!(width>0))throw new IllegalArgumentException("Invalid map coordinate.");
        return new Cell(j,(value-grid[j])/width,width);
    }
}
