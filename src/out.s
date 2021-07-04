.data
.align 2
toAlign: .space 404
MainANDi: .word 0
mainANDmessage: .word 0
mainANDi: .word 0
mainANDj: .word 0
literalAND1 : .asciiz "j = "
literalAND2 : .asciiz "i = "

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
la $t0, mainANDi
li $t1, 5
sw $t1, ($t0)
la $t2, mainANDj
li $t3, 0
sw $t3, ($t2)
loop1: 
lw $t4, mainANDi
li $t5, 0
slt $t5,  $t5,  $t4
beqz $t5, pool2
la $t6, mainANDj
lw $t7, mainANDj
li $t8, 1
add $t8,  $t8,  $t7
sw $t8, ($t6)
la $t9, mainANDi
lw $t1, mainANDi
li $t0, 1
sub $t0,  $t0,  $t1
sw $t0, ($t9)
b loop1
pool2: 
la $t3, mainANDmessage
la $s0, literalAND1
sw $s0, ($t3)
lw $s1, mainANDmessage
move $a0, $s1
jal print_string
lw $t2, mainANDj
move $a0, $t2
jal print_int
la $t4, mainANDmessage
la $s2, literalAND2
sw $s2, ($t4)
lw $s3, mainANDmessage
move $a0, $s3
jal print_string
lw $t5, mainANDi
move $a0, $t5
jal print_int
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
