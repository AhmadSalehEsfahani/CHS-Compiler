.data
.align 2
toAlign: .space 404
EXP: .asciiz "run time exception ..."
MainANDi: .word 0
mainANDi: .word 0
mainANDj: .word 0
mainANDn: .word 0
mainANDlist: .space 20
mainANDlistANDsize: .word 5
mainANDt: .word 0
literalAND1 : .asciiz "Sorted result is : \n"
literalAND2 : .asciiz " "

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
li $v0, 5
syscall
jr $ra
read_string: 
li $v0, 8
syscall
jr $ra
read_float: 
li $v0, 6
syscall
jr $ra
exception: 
la $a0, EXP
li $v0, 4
syscall
jr $ra
b termination
main:
la $t0, mainANDlist
la $t1, mainANDn
jal read_int
move $t2, $v0 
sw $t2, ($t1)
la $t3, mainANDi
li $t4, 0
sw $t4, ($t3)
loop1: 
lw $t5, mainANDi
lw $t6, mainANDn
slt $t6,  $t5,  $t6
beqz $t6, pool4
b BEGIN_STATEMENT3
BEGIN_UPDATE2:
la $t7, mainANDi
lw $t8, mainANDi
li $t9, 1
add $t9,  $t8,  $t9
sw $t9, ($t7)
b loop1
BEGIN_STATEMENT3:
la $t2, mainANDlist
lw $t1, mainANDi
lw $t4, mainANDlistANDsize
addi $t4, -1
sgt $t3, $t1, $t4
beq $t3, 1, exception
slt $t3, $t1, 0
beq $t3, 1, exception
mul $t1, $t1, 4
add $t2, $t2, $t1
jal read_int
move $t5, $v0 
sw $t5, ($t2)
b BEGIN_UPDATE2
pool4: 
la $t6, mainANDi
li $t8, 0
sw $t8, ($t6)
loop5: 
lw $t9, mainANDi
lw $t7, mainANDn
li $t4, 1
sub $t4,  $t7,  $t4
slt $t4,  $t9,  $t4
beqz $t4, pool13
b BEGIN_STATEMENT7
BEGIN_UPDATE6:
la $t3, mainANDi
lw $t1, mainANDi
li $t5, 1
add $t5,  $t1,  $t5
sw $t5, ($t3)
b loop5
BEGIN_STATEMENT7:
la $t8, mainANDj
li $t6, 0
sw $t6, ($t8)
loop8: 
lw $t7, mainANDj
lw $t9, mainANDn
lw $t4, mainANDi
sub $t4,  $t9,  $t4
li $t1, 1
sub $t1,  $t4,  $t1
slt $t1,  $t7,  $t1
beqz $t1, pool12
b BEGIN_STATEMENT10
BEGIN_UPDATE9:
la $t5, mainANDj
lw $t3, mainANDj
li $t6, 1
add $t6,  $t3,  $t6
sw $t6, ($t5)
b loop8
BEGIN_STATEMENT10:
la $s0, mainANDlist
lw $t8, mainANDj
lw $t9, mainANDlistANDsize
addi $t9, -1
sgt $t4, $t8, $t9
beq $t4, 1, exception
slt $t4, $t8, 0
beq $t4, 1, exception
mul $t8, $t8, 4
add $s0, $s0, $t8
lw $t7, ($s0)
la $s1, mainANDlist
lw $t1, mainANDj
li $t3, 1
add $t3,  $t1,  $t3
lw $t6, mainANDlistANDsize
addi $t6, -1
sgt $t5, $t3, $t6
beq $t5, 1, exception
slt $t5, $t3, 0
beq $t5, 1, exception
mul $t3, $t3, 4
add $s1, $s1, $t3
lw $t9, ($s1)
sgt $t9,  $t7,  $t9
beqz $t9, fi11
la $t4, mainANDt
la $s2, mainANDlist
lw $t8, mainANDj
lw $t1, mainANDlistANDsize
addi $t1, -1
sgt $t6, $t8, $t1
beq $t6, 1, exception
slt $t6, $t8, 0
beq $t6, 1, exception
mul $t8, $t8, 4
add $s2, $s2, $t8
lw $t5, ($s2)
sw $t5, ($t4)
la $t3, mainANDlist
lw $t7, mainANDj
lw $t9, mainANDlistANDsize
addi $t9, -1
sgt $t1, $t7, $t9
beq $t1, 1, exception
slt $t1, $t7, 0
beq $t1, 1, exception
mul $t7, $t7, 4
add $t3, $t3, $t7
la $s3, mainANDlist
lw $t6, mainANDj
li $t8, 1
add $t8,  $t6,  $t8
lw $t5, mainANDlistANDsize
addi $t5, -1
sgt $t4, $t8, $t5
beq $t4, 1, exception
slt $t4, $t8, 0
beq $t4, 1, exception
mul $t8, $t8, 4
add $s3, $s3, $t8
lw $t9, ($s3)
sw $t9, ($t3)
la $t1, mainANDlist
lw $t7, mainANDj
li $t6, 1
add $t6,  $t7,  $t6
lw $t5, mainANDlistANDsize
addi $t5, -1
sgt $t4, $t6, $t5
beq $t4, 1, exception
slt $t4, $t6, 0
beq $t4, 1, exception
mul $t6, $t6, 4
add $t1, $t1, $t6
lw $t8, mainANDt
sw $t8, ($t1)
fi11: 
b BEGIN_UPDATE9
pool12: 
b BEGIN_UPDATE6
pool13: 
la $s4, literalAND1
move $a0, $s4
jal print_string
la $t9, mainANDi
li $t7, 0
sw $t7, ($t9)
loop14: 
lw $t5, mainANDi
lw $t4, mainANDn
slt $t4,  $t5,  $t4
beqz $t4, pool17
b BEGIN_STATEMENT16
BEGIN_UPDATE15:
la $t6, mainANDi
lw $t8, mainANDi
li $t7, 1
add $t7,  $t8,  $t7
sw $t7, ($t6)
b loop14
BEGIN_STATEMENT16:
la $s5, mainANDlist
lw $t9, mainANDi
lw $t5, mainANDlistANDsize
addi $t5, -1
sgt $t4, $t9, $t5
beq $t4, 1, exception
slt $t4, $t9, 0
beq $t4, 1, exception
mul $t9, $t9, 4
add $s5, $s5, $t9
lw $t8, ($s5)
move $a0, $t8
jal print_int
la $s6, literalAND2
move $a0, $s6
jal print_string
b BEGIN_UPDATE15
pool17: 
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
