package org.chelonix.aoc2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CPU {

  private static final Map<Integer, Instruction> INSTRUCTION_MAP = Map.of(
      0, Instruction.ADV,
      1, Instruction.BXL,
      2, Instruction.BST,
      3, Instruction.JNZ,
      4, Instruction.BXC,
      5, Instruction.OUT,
      6, Instruction.BDV,
      7, Instruction.CDV
  );

  // 2,4, 1,5, 7,5, 1,6, 4,3, 5,5, 0,3, 3,0
  // BST A
  // BXL 5
  // CDV B
  // BXL 6
  // BXC
  // OUT B
  // ADV 3
  // JNZ 0

  // b = a mod 8
  // b = b xor 101b
  // c = a / 2^b
  // b = b xor 110b
  // b = b xor c
  // b % 8 -> outpout
  // a = a / 8
  // if a != 0 goto 0


  private static String comboToString(int operand) {
    if (operand <= 3) {
      return "%s".formatted(operand);
    } else if (operand == 4) {
      return "a";
    } else if (operand == 5) {
      return "b";
    } else if (operand == 6) {
      return "c";
    } else {
      throw new IllegalArgumentException("Invalid combo operand");
    }
  }

  private enum Instruction {

    ADV {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.a = cpu.a / (int)Math.pow(2, cpu.combo(operand));
      }

      @Override
      String toString(int operand) {
        return "a = a >> %s".formatted(comboToString(operand));
      }
    }, BXL {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.b = cpu.b ^ operand;
      }

      @Override
      String toString(int operand) {
        return "b ^= %s".formatted(operand);
      }
    }, BST {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.b = cpu.combo(operand) % 8;
      }

      @Override
      String toString(int operand) {
        return "b = %s %% 8".formatted(comboToString(operand));
      }
    }, JNZ {
      @Override
      void execute(int operand, CPU cpu) {
        if (cpu.a != 0) {
          cpu.pc = operand;
        }
      }

      @Override
      String toString(int operand) {
        return "if a != 0 goto %s".formatted(operand);
      }
    },
    BXC {
      @Override
      void execute(int __, CPU cpu) {
        cpu.b = cpu.b ^ cpu.c;
      }

      @Override
      String toString(int operand) {
        return "b ^= c";
      }
    }, OUT {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.outBuufer.add(cpu.combo(operand) % 8);
      }

      @Override
      String toString(int operand) {
        return "(%s %% 8) -> output".formatted(comboToString(operand));
      }
    }, BDV {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.b = cpu.a / (int)Math.pow(2, cpu.combo(operand));
      }

      @Override
      String toString(int operand) {
        return "b = a >> %s".formatted(comboToString(operand));
      }
    }, CDV {
      @Override
      void execute(int operand, CPU cpu) {
        cpu.c = cpu.a / (int)Math.pow(2, cpu.combo(operand));
      }

      @Override
      String toString(int operand) {
        return "c = a >> %s".formatted(comboToString(operand));
      }
    };

    abstract void execute(int operand, CPU cpu);

    abstract String toString(int operand);
  }

  private long a, b, c;
  private int pc;
  private Program program;
  private boolean halt;
  private List<Long> outBuufer;

  public CPU() {
    this.a = 0;
    this.b = 0;
    this.c = 0;
    this.pc = 0;
    this.program = null;
    this.halt = true;
    this.outBuufer = new ArrayList<>();
  }

  private void init(Program program) {
    this.pc = 0;
    this.program = program;
    this.a = program.a();
    this.b = program.b();
    this.c = program.c();
    this.halt = false;
    this.outBuufer.clear();
  }

  public boolean isHalted() {
    return halt;
  }

  private long combo(int operand) {
    if (operand <= 3) {
      return operand;
    } else if (operand == 4) {
      return a;
    } else if (operand == 5) {
      return b;
    } else if (operand == 6) {
      return c;
    } else {
      throw new IllegalArgumentException("Invalid combo operand");
    }
  }

  public List<Long> run(Program program) {
    init(program);
    while (!halt) {
      tick();
    }
    return outBuufer;
  }

  public List<Long> run(Program program, long a) {
    init(program);
    this.a = a;
    while (!halt) {
      tick();
    }
    return outBuufer;
  }

  /**
   * Execute the next instruction.
   */
  public void tick() {
    int opcode = program.code().get(pc++);
    int operand = program.code().get(pc++);
    INSTRUCTION_MAP.get(opcode).execute(operand, this);
    if (pc >= program.code().size()) {
      halt = true;
    }
  }

  public String decompile(Program program) {
    init(program);
    StringBuilder sb = new StringBuilder();
    while (!halt) {
      int opcode = program.code().get(pc++);
      int operand = program.code().get(pc++);
      sb.append(INSTRUCTION_MAP.get(opcode).toString(operand)).append("\n");
      if (pc >= program.code().size()) {
        halt = true;
      }
    }
    return sb.toString();
  }
}
