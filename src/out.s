.data
.align 2
MainANDi: .word 0
mainANDz: .space 400
mainANDzANDsize: .word 100
mainANDres: .word 0
mainANDj: .word 0
mainANDfloatNum: .float 0.0
mainANDmessage: .word 0
literalAND1 : .ascii "it is result : "
literalAND2 : .ascii " "

.text
.globl main
print_string: 
li $v0, 4
syscall
jr $ra
print_int: 
li $v0, 1
syscall
jr $ra
print_float: 
li $v0, 2
syscall
jr $ra
read_int: 
read_string: 
read_float: 
exception: 
main:
la $t0, mainANDj
li $t1, 5
sw $t1, ($t0)
la $t2, MainANDi
li $t3, 1
sw $t3, ($t2)
la $t4, mainANDz
la $t5, mainANDz
lw $t6, MainANDi
lw $t7, mainANDzANDsize
sge $t8, $t6, $t7
slt $t8, $t6, 0
beq $t8, 1, exception
mul $t6, $t6, 4
add $t5, $t5, $t6
li $t9, 5
sw $t9, ($t5)
la $t1, mainANDres
la $s0, mainANDz
lw $t0, MainANDi
lw $t3, mainANDzANDsize
sge $t2, $t0, $t3
slt $t2, $t0, 0
beq $t2, 1, exception
mul $t0, $t0, 4
add $s0, $s0, $t0
lw $t7, ($s0)
li $t8, 5
mulo $t8,  $t8,  $t7
sw $t8, ($t1)
la $t6, mainANDmessage
la $s1, literalAND1
sw $s1, ($t6)
la $t9, mainANDfloatNum
li.s $f0, 1.1
li.s $f1, 2.3e-3
mul.s $f1,  $f1,  $f0
s.s $f1, ($t9)
la $s2, mainANDmessage
move $a0, $s2
jal print_string
lw $t3, mainANDres
li $t2, 5
add $t2,  $t2,  $t3
move $a0, $t2
jal print_int
la $s3, literalAND2
move $a0, $s3
jal print_string
l.s $f2, mainANDfloatNum
mov.s $f12, $f2
jal print_float
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
