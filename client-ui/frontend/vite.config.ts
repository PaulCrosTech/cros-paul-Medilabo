import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(() => {
    return {
        plugins: [react()],
        server: {
            port: 9080,
        }
        ,envPrefix: ['MS_GATEWAY_USER','MS_GATEWAY_PASSWORD','VITE_']
    };

});
