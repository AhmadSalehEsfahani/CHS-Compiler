.data
.align 2
toAlign: .space 404
MainANDi: .word 0
mainANDmessage: .word 0
mainANDi: .word 0
mainANDj: .word 0
mainANDlist: .space 20
mainANDlistANDsize: .word 5
literalAND1 : .asciiz "elements are : \n"
literalAND2 : .asciiz " + "

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
main:
la $t0, mainANDlist
la $t1, mainANDi
li $t2, 0
sw $t2, ($t1)
loop1: 
lw $t3, mainANDi
li $t4, 5
slt $t4,  $t3,  $t4
beqz $t4, pool4
b BEGIN_STATEMENT3
BEGIN_UPDATE2:
la $t5, mainANDi
lw $t6, mainANDi
li $t7, 1
add $t7,  $t6,  $t7
sw $t7, ($t5)
b loop1
BEGIN_STATEMENT3:
la $t8, mainANDlist
lw $t9, mainANDi
lw $t2, mainANDlistANDsize
sge $t1, $t9, $t2
slt $t1, $t9, 0
beq $t1, 1, exception
mul $t9, $t9, 4
add $t8, $t8, $t9
lw $t3, mainANDi
li $t4, 5
mulo $t4,  $t3,  $t4
sw $t4, ($t8)
b BEGIN_UPDATE2
pool4: 
la $s0, literalAND1
move $a0, $s0
jal print_string
la $t6, mainANDj
li $t7, 0
sw $t7, ($t6)
loop5: 
lw $t5, mainANDj
li $t2, 5
slt $t2,  $t5,  $t2
beqz $t2, pool8
b BEGIN_STATEMENT7
BEGIN_UPDATE6:
la $t1, mainANDj
lw $t9, mainANDj
li $t3, 1
add $t3,  $t9,  $t3
sw $t3, ($t1)
b loop5
BEGIN_STATEMENT7:
la $s1, mainANDlist
lw $t4, mainANDj
lw $t7, mainANDlistANDsize
sge $t6, $t4, $t7
slt $t6, $t4, 0
beq $t6, 1, exception
mul $t4, $t4, 4
add $s1, $s1, $t4
lw $t5, ($s1)
move $a0, $t5
jal print_int
la $s2, literalAND2
move $a0, $s2
jal print_string
b BEGIN_UPDATE6
pool8: 
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
