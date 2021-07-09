.data
.align 2
toAlign: .space 404
MainANDi: .word 0
MainANDj: .word 0
MainANDn: .word 0
MainANDlist: .space 400
MainANDlistANDsize: .word 100
MainANDbubbleSortANDt: .word 0
mainANDt: .word 0
literalAND1 : .asciiz "sorted result is : "
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
MainANDbubbleSort:
la $t0, MainANDi
li $t1, 0
sw $t1, ($t0)
loop1: 
lw $t2, MainANDi
lw $t3, MainANDn
li $t4, 1
sub $t4,  $t3,  $t4
slt $t4,  $t2,  $t4
beqz $t4, pool9
b BEGIN_STATEMENT3
BEGIN_UPDATE2:
la $t5, MainANDi
lw $t6, MainANDi
li $t7, 1
add $t7,  $t6,  $t7
sw $t7, ($t5)
b loop1
BEGIN_STATEMENT3:
la $t8, MainANDj
li $t9, 0
sw $t9, ($t8)
loop4: 
lw $t1, MainANDj
lw $t0, MainANDn
lw $t3, MainANDi
sub $t3,  $t0,  $t3
li $t2, 1
sub $t2,  $t3,  $t2
slt $t2,  $t1,  $t2
beqz $t2, pool8
b BEGIN_STATEMENT6
BEGIN_UPDATE5:
la $t4, MainANDj
lw $t6, MainANDj
li $t7, 1
add $t7,  $t6,  $t7
sw $t7, ($t4)
b loop4
BEGIN_STATEMENT6:
la $s0, MainANDlist
lw $t5, MainANDj
lw $t9, MainANDlistANDsize
sge $t8, $t5, $t9
slt $t8, $t5, 0
beq $t8, 1, exception
mul $t5, $t5, 4
add $s0, $s0, $t5
lw $t0, ($s0)
la $s1, MainANDlist
lw $t3, MainANDj
li $t1, 1
add $t1,  $t3,  $t1
lw $t2, MainANDlistANDsize
sge $t6, $t1, $t2
slt $t6, $t1, 0
beq $t6, 1, exception
mul $t1, $t1, 4
add $s1, $s1, $t1
lw $t7, ($s1)
sgt $t7,  $t0,  $t7
beqz $t7, fi7
la $t4, MainANDbubbleSortANDt
la $s2, MainANDlist
lw $t9, MainANDj
lw $t8, MainANDlistANDsize
sge $t5, $t9, $t8
slt $t5, $t9, 0
beq $t5, 1, exception
mul $t9, $t9, 4
add $s2, $s2, $t9
lw $t3, ($s2)
sw $t3, ($t4)
la $t2, MainANDlist
lw $t6, MainANDj
lw $t1, MainANDlistANDsize
sge $t0, $t6, $t1
slt $t0, $t6, 0
beq $t0, 1, exception
mul $t6, $t6, 4
add $t2, $t2, $t6
la $s3, MainANDlist
lw $t7, MainANDj
li $t8, 1
add $t8,  $t7,  $t8
lw $t5, MainANDlistANDsize
sge $t9, $t8, $t5
slt $t9, $t8, 0
beq $t9, 1, exception
mul $t8, $t8, 4
add $s3, $s3, $t8
lw $t3, ($s3)
sw $t3, ($t2)
la $t4, MainANDlist
lw $t1, MainANDj
li $t0, 1
add $t0,  $t1,  $t0
lw $t6, MainANDlistANDsize
sge $t7, $t0, $t6
slt $t7, $t0, 0
beq $t7, 1, exception
mul $t0, $t0, 4
add $t4, $t4, $t0
lw $t5, MainANDbubbleSortANDt
sw $t5, ($t4)
fi7: 
b BEGIN_UPDATE5
pool8: 
b BEGIN_UPDATE2
pool9: 
li $t9, 1
jr $ra 
main:
la $t8, MainANDlist
la $t3, MainANDn
jal read_int
move $t1, $v0 
sw $t1, ($t3)
la $t6, MainANDi
li $t7, 0
sw $t7, ($t6)
loop10: 
lw $t0, MainANDi
lw $t5, MainANDn
slt $t5,  $t0,  $t5
beqz $t5, pool13
b BEGIN_STATEMENT12
BEGIN_UPDATE11:
la $t1, MainANDi
lw $t3, MainANDi
li $t7, 1
add $t7,  $t3,  $t7
sw $t7, ($t1)
b loop10
BEGIN_STATEMENT12:
la $t6, MainANDlist
lw $t0, MainANDi
lw $t5, MainANDlistANDsize
sge $t3, $t0, $t5
slt $t3, $t0, 0
beq $t3, 1, exception
mul $t0, $t0, 4
add $t6, $t6, $t0
jal read_int
move $t7, $v0 
sw $t7, ($t6)
b BEGIN_UPDATE11
pool13: 
la $t1, mainANDt
jal MainANDbubbleSort
sw $t1, ($t9)
lw $t5, mainANDt
move $a0, $t5
jal print_int
la $s4, literalAND1
move $a0, $s4
jal print_string
la $t3, MainANDi
li $t0, 0
sw $t0, ($t3)
loop14: 
lw $t7, MainANDi
lw $t1, MainANDn
slt $t1,  $t7,  $t1
beqz $t1, pool17
b BEGIN_STATEMENT16
BEGIN_UPDATE15:
la $t9, MainANDi
lw $t5, MainANDi
li $t0, 1
add $t0,  $t5,  $t0
sw $t0, ($t9)
b loop14
BEGIN_STATEMENT16:
la $s5, MainANDlist
lw $t3, MainANDi
lw $t7, MainANDlistANDsize
sge $t1, $t3, $t7
slt $t1, $t3, 0
beq $t1, 1, exception
mul $t3, $t3, 4
add $s5, $s5, $t3
lw $t5, ($s5)
move $a0, $t5
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
