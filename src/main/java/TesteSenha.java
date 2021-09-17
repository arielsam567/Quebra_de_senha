public class TesteSenha
{
    public static void main(final String[] array) {
        final String anObject = array[0];
        final String anObject2 = array[1];
        if ("administrador".equals(anObject) && "VbbmR4".equals(anObject2)) {
            System.out.println("Acesso concedido!");
            System.exit(0);
        }
    }
}