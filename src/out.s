.data
.align 2
toAlign: .space 404
MainANDi: .word 0
mainANDmessage: .word 0
mainANDi: .word 0
literalAND1 : .asciiz "not working "
literalAND2 : .asciiz "i is equal to 5 "
literalAND3 : .asciiz "i is not equal to 5 "

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
la $t0, mainANDmessage
la $s0, literalAND1
sw $s0, ($t0)
la $t1, mainANDi
li $t2, 4
sw $t2, ($t1)
lw $t3, mainANDi
li $t4, 5
seq $t4,  $t4,  $t3
beqz $t4, else1
la $t5, mainANDmessage
la $s1, literalAND2
sw $s1, ($t5)
b esle2
else1:
la $t6, mainANDmessage
la $s2, literalAND3
sw $s2, ($t6)
esle2:
lw $s3, mainANDmessage
move $a0, $s3
jal print_string
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
