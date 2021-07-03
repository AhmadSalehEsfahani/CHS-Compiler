.data
.align 2
MainANDi: .word 0
mainANDz: .space 400
mainANDzANDsize: .word 100
mainANDres: .word 0

.text
.globl main
print_string: 
print_int: 
print_float: 
read_int: 
read_string: 
read_float: 
exception: 
main:
la $t0, MainANDi
li $t1, 1
sw $t1, ($t0)
la $t2, mainANDz
la $t3, mainANDz
lw $t4, MainANDi
lw $t5, mainANDzANDsize
sge $t6, $t4, $t5
slt $t6, $t4, 0
beq $t6, 1, exception
mul $t4, $t4, 4
add $t3, $t3, $t4
li $t7, 5
sw $t7, ($t3)
la $t8, mainANDres
la $s0, mainANDz
lw $t9, MainANDi
lw $t1, mainANDzANDsize
sge $t0, $t9, $t1
slt $t0, $t9, 0
beq $t0, 1, exception
mul $t9, $t9, 4
add $s0, $s0, $t9
lw $t5, ($s0)
sw $t5, ($t8)
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
