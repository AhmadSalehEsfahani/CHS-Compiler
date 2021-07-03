.data
MainANDi: .word 0
mainANDa: .float 0.0
mainANDj: .word 0
mainANDz: .word 0
mainANDres: .word 0

.text
.globl main
main:
la $t0, mainANDa
li.s $f0 ,0.2e-3
s.s $f0, ($t0)
la $t1, MainANDi
li $t2 ,3
sw $t2, ($t1)
la $t3, mainANDj
li $t4 ,2
sw $t4, ($t3)
la $t5, mainANDz
li $t6 ,2
sw $t6, ($t5)
la $t7, mainANDres
lw $t8 ,MainANDi
lw $t9 ,mainANDj
lw $t2 ,mainANDz
mulo $t2 , $t2 , $t9
add $t2 , $t2 , $t8
sw $t2, ($t7)
print_string: 
print_int: 
print_float: 
read_int: 
read_string: 
read_float: 
exception: 
termination: 
li $v0, 10
li $t0, 0
move $a0, $t0
syscall
