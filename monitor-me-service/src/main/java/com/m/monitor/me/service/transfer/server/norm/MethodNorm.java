package com.m.monitor.me.service.transfer.server.norm;

import com.m.monitro.me.common.utils.DoubleUtil;

public class MethodNorm{
        private String m;
        private long min;
        private long max;
        private long sum;
        private long total;
        private double avg;

        public String getM() {
                return m;
        }

        public void setM(String m) {
                this.m = m;
        }

        public long getMin() {
                return min;
        }

        public void setMin(long min) {
                this.min = min;
        }

        public long getMax() {
                return max;
        }

        public void setMax(long max) {
                this.max = max;
        }

        public long getSum() {
                return sum;
        }

        public void setSum(long sum) {
                this.sum = sum;
        }

        public long getTotal() {
                return total;
        }

        public void setTotal(long total) {
                this.total = total;

        }

        public double getAvg() {
                return avg;
        }

        public void setAvg() {
                this.avg=DoubleUtil.avg(sum,total);
        }
}