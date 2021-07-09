.data
.align 2
toAlign: .space 404
EXP: .asciiz "run time exception ..."
MainANDi: .word 0
mainANDi: .word 0
mainANDj: .word 0
mainANDn: .word 0
mainANDlist: .space 400
mainANDlistANDsize: .word 100
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
li $t2, 1
add $t2,  $t8,  $t2
sw $t2, ($t7)
b loop1
BEGIN_STATEMENT3:
la $t1, mainANDlist
lw $t4, mainANDi
lw $t3, mainANDlistANDsize
addi $t3, -1
sgt $t5, $t4, $t3
beq $t5, 1, exception
slt $t5, $t4, 0
beq $t5, 1, exception
mul $t4, $t4, 4
add $t1, $t1, $t4
jal read_int
move $t6, $v0 
sw $t6, ($t1)
b BEGIN_UPDATE2
pool4: 
la $t8, mainANDi
li $t2, 0
sw $t2, ($t8)
loop5: 
lw $t7, mainANDi
lw $t3, mainANDn
li $t5, 1
sub $t5,  $t3,  $t5
slt $t5,  $t7,  $t5
beqz $t5, pool13
b BEGIN_STATEMENT7
BEGIN_UPDATE6:
la $t4, mainANDi
lw $t6, mainANDi
li $t2, 1
add $t2,  $t6,  $t2
sw $t2, ($t4)
b loop5
BEGIN_STATEMENT7:
la $t8, mainANDj
li $t3, 0
sw $t3, ($t8)
loop8: 
lw $t7, mainANDj
lw $t5, mainANDn
lw $t6, mainANDi
sub $t6,  $t5,  $t6
li $t2, 1
sub $t2,  $t6,  $t2
slt $t2,  $t7,  $t2
beqz $t2, pool12
b BEGIN_STATEMENT10
BEGIN_UPDATE9:
la $t4, mainANDj
lw $t3, mainANDj
li $t8, 1
add $t8,  $t3,  $t8
sw $t8, ($t4)
b loop8
BEGIN_STATEMENT10:
la $s0, mainANDlist
lw $t5, mainANDj
lw $t6, mainANDlistANDsize
addi $t6, -1
sgt $t7, $t5, $t6
beq $t7, 1, exception
slt $t7, $t5, 0
beq $t7, 1, exception
mul $t5, $t5, 4
add $s0, $s0, $t5
lw $t2, ($s0)
la $s1, mainANDlist
lw $t3, mainANDj
li $t8, 1
add $t8,  $t3,  $t8
lw $t4, mainANDlistANDsize
addi $t4, -1
sgt $t6, $t8, $t4
beq $t6, 1, exception
slt $t6, $t8, 0
beq $t6, 1, exception
mul $t8, $t8, 4
add $s1, $s1, $t8
lw $t7, ($s1)
sgt $t7,  $t2,  $t7
beqz $t7, fi11
la $t5, mainANDt
la $s2, mainANDlist
lw $t3, mainANDj
lw $t4, mainANDlistANDsize
addi $t4, -1
sgt $t6, $t3, $t4
beq $t6, 1, exception
slt $t6, $t3, 0
beq $t6, 1, exception
mul $t3, $t3, 4
add $s2, $s2, $t3
lw $t8, ($s2)
sw $t8, ($t5)
la $t2, mainANDlist
lw $t7, mainANDj
lw $t4, mainANDlistANDsize
addi $t4, -1
sgt $t6, $t7, $t4
beq $t6, 1, exception
slt $t6, $t7, 0
beq $t6, 1, exception
mul $t7, $t7, 4
add $t2, $t2, $t7
la $s3, mainANDlist
lw $t3, mainANDj
li $t8, 1
add $t8,  $t3,  $t8
lw $t5, mainANDlistANDsize
addi $t5, -1
sgt $t4, $t8, $t5
beq $t4, 1, exception
slt $t4, $t8, 0
beq $t4, 1, exception
mul $t8, $t8, 4
add $s3, $s3, $t8
lw $t6, ($s3)
sw $t6, ($t2)
la $t7, mainANDlist
lw $t3, mainANDj
li $t5, 1
add $t5,  $t3,  $t5
lw $t4, mainANDlistANDsize
addi $t4, -1
sgt $t8, $t5, $t4
beq $t8, 1, exception
slt $t8, $t5, 0
beq $t8, 1, exception
mul $t5, $t5, 4
add $t7, $t7, $t5
lw $t6, mainANDt
sw $t6, ($t7)
fi11: 
b BEGIN_UPDATE9
pool12: 
b BEGIN_UPDATE6
pool13: 
la $s4, literalAND1
move $a0, $s4
jal print_string
la $t3, mainANDi
li $t4, 0
sw $t4, ($t3)
loop14: 
lw $t8, mainANDi
lw $t5, mainANDn
slt $t5,  $t8,  $t5
beqz $t5, pool17
b BEGIN_STATEMENT16
BEGIN_UPDATE15:
la $t6, mainANDi
lw $t4, mainANDi
li $t3, 1
add $t3,  $t4,  $t3
sw $t3, ($t6)
b loop14
BEGIN_STATEMENT16:
la $s5, mainANDlist
lw $t8, mainANDi
lw $t5, mainANDlistANDsize
addi $t5, -1
sgt $t4, $t8, $t5
beq $t4, 1, exception
slt $t4, $t8, 0
beq $t4, 1, exception
mul $t8, $t8, 4
add $s5, $s5, $t8
lw $t3, ($s5)
move $a0, $t3
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
