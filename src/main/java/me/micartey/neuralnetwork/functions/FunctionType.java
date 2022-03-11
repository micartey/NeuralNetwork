package me.micartey.neuralnetwork.functions;

@SuppressWarnings("all")
public enum FunctionType {

    SIGMOID {
        @Override
        public double calculate(double input) {
            return 1D / (1 + Math.exp(-input));
        }
    },
    IDENTITY {
        @Override
        public double calculate(double input) {
            return input;
        }
    },
    BINARY {
        @Override
        public double calculate(double input) {
            return input < 0 ? 0 : 1;
        }
    },
    TANH {
        @Override
        public double calculate(double input) {
            return Math.tanh(input);
        }
    },
    ARCTAN {
        @Override
        public double calculate(double input) {
            return Math.atan(Math.toRadians(input));
        }
    },
    ReLU {
        @Override
        public double calculate(double input) {
            return Math.max(0, input);
        }
    },
    PReLU {
        @Override
        public double calculate(double input) {
            return input < 0 ? .01 * input : input;
        }
    },
    ELU {
        @Override
        public double calculate(double input) {
            return input < 0 ? .25 * (Math.exp(input) - 1) : input;
        }
    },
    SOFT {
        @Override
        public double calculate(double input) {
            return Math.log(1 + Math.exp(input));
        }
    },
    EXP {
        @Override
        public double calculate(double input) {
            return Math.pow(-1, input);
        }
    }

    ;

    public double calculate(double input) {
        throw new RuntimeException("FunctionType." + this.name() + " hasn't been implemented yet.");
    }
}
